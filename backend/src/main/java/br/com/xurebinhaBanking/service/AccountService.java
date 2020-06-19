package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.repository.AccountRepository;
import br.com.xurebinhaBanking.repository.ClientRepository;
import br.com.xurebinhaBanking.model.account.Account;
import br.com.xurebinhaBanking.model.client.Client;
import br.com.xurebinhaBanking.model.invoice.Invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountService {

    private static String NOVA_LINHA = "\n";
    private static boolean FIM_MENU_CONTA = false;
    private H2JDBCUtils conn;
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private TransactionService transactionService;
    private static Scanner in;

    public AccountService(H2JDBCUtils conn) {
        this.conn = conn;
        this.accountRepository = new AccountRepository(conn);
        this.clientRepository = new ClientRepository(conn);
        this.transactionService = new TransactionService(conn);
        this.in = new Scanner(System.in);
    }

    public AccountService() {

    }

    public void actionAccount() {

        System.out.println("---------- ACESSO A CONTA ------------");
        System.out.println("-------- Clientes cadastrados: ----------");
        String listCLients = clientRepository.listClients();
        System.out.println(listCLients);
        System.out.println("Digite o ID do usuario que deseja:");

        boolean validaSelecaoCliente = false;
        int selClient = in.nextInt();
        do {
            validaSelecaoCliente = clientRepository.existClient(selClient);
            if (!validaSelecaoCliente) {
                System.out.println("Cliente nao encontrado, selecione outro:");
                System.out.println(listCLients);
                selClient = in.nextInt();
                //todo ajustar para sair, caso queira
            }
        } while (!validaSelecaoCliente);

        validateFirstPassword(selClient);
        //find on
        Client client = clientRepository.findClient(selClient);
        do {
            System.out.println(menu());

            System.out.println("Digite sua opcao:");
            int acao = in.nextInt();

            switch (acao) {
                case 1:
                    makeTransfer(client, listCLients);
                    break;
                case 2:
                    accountDeposit(client);
                    break;
                case 3:
                    viewBalance(client);
                    break;
                case 4:
                    payBills(client);
                    break;
                case 5:
                    makeLoan(client);
                    break;
                case 6:
                    break;
                case 7:
                    updateAccount(client);
                    break;
                case 8:
                    changePassword(client);
                    break;
                case 0:
                default:
                    FIM_MENU_CONTA = true;
                    break;
            }
        } while (!FIM_MENU_CONTA);
    }

    private void validateFirstPassword(int selClient) {
        boolean validaSenhaCliente = false;
        System.out.println("Digite a Senha do usuario:");
        String senhaClient = in.next();
        do {
            validaSenhaCliente = clientRepository.passwordOk(selClient, senhaClient);
            if (!validaSenhaCliente) {
                System.out.println("Senha do cliente '" + selClient + "' nao confere, tente novamente:");
                senhaClient = in.next();

            }
        } while (!validaSenhaCliente);
    }

    /*
    - Transferência de valores (solicitando uma segunda senha de segurança e respeitando um limite definido pelo tipo de conta -
    universitária até 500 reais em dias de semana e 250 em todo o fim de semana, conta corrente padrão até 5000 reais em dias de
    semana e 1500 em todo o fim de semana, conta premium até 30000 reais em dias de semana e 15000 reais em todo o fim de semana)
    o valor é debitado da conta e a operação aparece no extrato; entre clientes Xurebinha (codigo bancario 666)
    não há tarifas e o valor é disponibilizado na hora; para clientes de outros bancos, a tarifa depende do tipo de conta e os
    valores aparecem nos outros bancos em até 48 horas (unversitária tem 2 transferencias gratuitas mensais, depois 10 por
    cada transferencia; padrão tem 4 e depois sao 10 reais; conta premium não tem limite mensal e paga somente 6 reais)
     */
    private void makeTransfer(Client client, String listCLients) {
        validateSecondPassword(client);
        System.out.println("Para a transferencia selecione a conta origem:");
        Account selAccountOut = selectAccount(client);
        System.out.println("Selecione o cliente destino:");

        boolean validaSelecaoClienteDestino = false;
        int selClientDest = in.nextInt();
        do {
            validaSelecaoClienteDestino = clientRepository.existClient(selClientDest);
            if (!validaSelecaoClienteDestino) {
                System.out.println("Cliente nao encontrado, selecione outro:");
                System.out.println(listCLients);
                selClientDest = in.nextInt();
                //todo ajustar para sair, caso queira
            }
        } while (!validaSelecaoClienteDestino);

        Client clientDest = clientRepository.findClient(selClientDest);

        System.out.println("Funcao ainda nao terminada");

    }

    private void makeLoan(Client client){
        System.out.println("-----FAZER EMPRESTIMO-----");
        Account account = selectAccount(client);
        int loanLimit = account.getLimitAccount().intValue();

        if (loanLimit > 0) {
            int loanAmount;
            do {
                System.out.println("Valor disponível para empréstimo: R$ " + account.getLimitAccount());
                loanAmount = in.nextInt();
            } while ((loanAmount > loanLimit) || (loanAmount < 1));

            loanAmount = 10;

            // questionar quantas vezes o cliente quer fazer, até 10
            // apresentar para o usuário os valores a receber e pagar e gravar a transaction
            // atualizar saldo e limite do cliente
        } else {
            // mensagem de sem limite para empréstimo
            // mostrar menu para voltar
        }
    }

    //- Pagamento de contas (solicitando uma segunda senha e um código de barras válido em algum formato específico a escolher pelo grupo)
    //o valor é debitado da conta e a operação aparece no extrato
    private void payBills(Client client) {
        System.out.println("Para o pagamento de contas, forneca sua segunda senha.");
        validateSecondPassword(client);

        Account selAccount = selectAccount(client);

        System.out.println("Digite um codigo de barras valido:");
        String codigoBarras = in.next();

        /*boolean validaCodigoBarras = false;
        do {
            validaCodigoBarras = checkCode(codigoBarras);
            if (!validaCodigoBarras) {
                System.out.println("Codigo de barras '" + codigoBarras + "' nao é válido, tente novamente:");
                codigoBarras = in.next();
                //todo ajustar para sair, caso queira
            }
        } while (!validaCodigoBarras);*/

        Invoice invoice = new Invoice(codigoBarras);
        if (getAllFunds(selAccount).compareTo(invoice.getValue()) >= 1) {
            //adicionar pagamento nas transacoes
            transactionService.createPaymentTransaction(selAccount.getId(), invoice);

            //debitar valor da conta
            selAccount.getBalance().subtract(invoice.getValue());

            //Atualizar o saldo
            accountRepository.updateBalance(selAccount);

        } else {
            System.out.println("O cliente " + client.getName() + " não possui saldo suficiente!");
        }
    }

    private void validateSecondPassword(Client client) {
        boolean validaSenhaCliente = false;
        String secondPassClient = in.next();
        System.out.println("Digite a SEGUNDA SENHA do usuario:");
        do {
            validaSenhaCliente = clientRepository.secondPasswordOk(client.getId(), secondPassClient);
            if (!validaSenhaCliente) {
                System.out.println("Segunda Senha do cliente '" + client.getId() + "' nao confere, tente novamente:");
                secondPassClient = in.next();
                //todo ajustar para sair, caso queira
            }
        } while (!validaSenhaCliente);
        //validaSenhaCliente;
    }

    private Account selectAccount(Client client) {
        System.out.println("-------- Contas existentes: ----------");
        String listAccounts = accountRepository.listAccounts(client.getId());
        System.out.println(listAccounts);
        System.out.println("Digite o ID da conta que deseja:");
        boolean validaSelecaoAccount = false;
        int selAccount = in.nextInt();
        do {
            validaSelecaoAccount = accountRepository.existAccount(selAccount);
            if (!validaSelecaoAccount) {
                System.out.println("Conta nao encontrado, selecione outra:");
                System.out.println(listAccounts);
                selAccount = in.nextInt();
                //todo ajustar para sair, caso queira
            }
        } while (!validaSelecaoAccount);

        for (int i = 0; i < client.getAccountList().size(); i++) {
            if (client.getAccountList().get(i).getId() == selAccount)
                return client.getAccountList().get(i);
        }

        return null;
    }

    private BigDecimal getAllFunds(Account account) {
        return account.getLimitAccount().add(account.getBalance());
    }

    private boolean checkCode(String codigoBarras) {
        return codigoBarras.length() == 26;
    }

    private void viewBalance(Client client) {
        if (client.getAccountList().size() > 0) {

            System.out.println("CLIENTE: " + client.getName() + " CPF: " + client.getCpf());
            for (int i = 0; i < client.getAccountList().size(); i++) {
                Account acc = client.getAccountList().get(i);
                System.out.println("BANCO: " + acc.getBank().getCod() + " - " + acc.getBank().getName());
                System.out.println("AGENCIA : " + acc.getAgency() + " NUM. CONTA: " + acc.getNumber());
                System.out.println("STATUS: " + acc.getStatusAccount().toString());
                System.out.println("TIPO DE CONTA: " + acc.getAccountType().getNameAccountType());
                System.out.println("-------------------");
                System.out.println("SALDO: " + acc.getBalance() + "  LIMITE: " + acc.getLimitAccount());
            }
        } else {
            System.out.println("CLIENTE: " + client.getName() + " não possui contas.");
        }
    }

    private void updateAccount(Client client) {
        boolean finalize = false;
        List<Integer> accountsIds = new ArrayList<>();

        if (!client.getAccountList().isEmpty()) {
            client.getAccountList().forEach(account -> {
                accountsIds.add(account.getId());
                System.out.println("Conta id: " + account.getId());
            });
            System.out.println("Digite o id da conta: ");
            int accountId = in.nextInt();

            Account account = client.getAccountList().get(0);

            do {
                System.out.println(menuAccount());

                System.out.println("Digite sua opcao:");
                int option = in.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Digite o limite desejado:");
                        account.setLimitAccount(in.nextBigDecimal());
                        accountRepository.updateLimit(account);
                        break;
                    case 2:
                        System.out.println("Digite o número:");
                        account.setNumber(in.nextInt());
                        accountRepository.updateNumber(account);
                        break;
                    case 0:
                    default:
                        finalize = true;
                        break;
                }
            } while (finalize);
        } else {
            System.out.println("Este cliente não tem contas cadastradas.");
        }
    }

    private void changePassword(Client client) {
        Scanner in = new Scanner(System.in);
        System.out.println(client.getName() + " DIGITE SUA SENHA ATUAL: ");
        String passwordClient = in.next();
        boolean validaSenhaCliente = false;

        do {
            validaSenhaCliente = clientRepository.passwordOk(client.getId(), passwordClient);
            if (!validaSenhaCliente) {
                System.out.println("Senha incorreta, tente novamente:");
                passwordClient = in.next();
            }
        } while (!validaSenhaCliente);

        System.out.println(client.getName() + " DIGITE SUA SEGUNDA SENHA ATUAL: ");
        String secondPasswordClient = in.next();
        boolean validaSegundaSenhaCliente = false;
        do {
            validaSegundaSenhaCliente = clientRepository.secondPasswordOk(client.getId(), secondPasswordClient);
            if (!validaSegundaSenhaCliente) {
                System.out.println("Senha incorreta, tente novamente:");
                secondPasswordClient = in.next();
            }
        } while (!validaSegundaSenhaCliente);

        System.out.println("DIGITE SUA NOVA SENHA");
        String newPasswordClient = in.next();

        System.out.println("DIGITE SUA NOVA SEGUNDA SENHA");
        String newSecondPasswordClient = in.next();

        client.setPassword(newPasswordClient);
        client.setSecondPassword(newSecondPasswordClient);

        clientRepository.changePasswordBd(client);
    }


    private void accountDeposit(Client client) {
        Account account = new Account();
        Scanner in = new Scanner(System.in);
        System.out.println("Selecione o id da conta escolhida:");
        account.setId(in.nextInt());

        System.out.println("Digite o valor do depósito:");
        account.setBalance(new BigDecimal(in.nextInt()));

        accountRepository.updateBalance(account);
    }
    
    public static String menu() {
        return "---------------------------------" + NOVA_LINHA +
                "----MENU DE CONTA DO CLIENTE----" + NOVA_LINHA +
                "---------------------------------" + NOVA_LINHA +
                "1 - Fazer Transferencia" + NOVA_LINHA +
                "2 - Fazer Depósito" + NOVA_LINHA +
                "3 - Verificar Saldo" + NOVA_LINHA +
                "4 - Pagar Conta " + NOVA_LINHA +
                "5 - Fazer Emprestimo" + NOVA_LINHA +
                "6 - Pagar Emprestimo" + NOVA_LINHA +
                "7 - Atualizar Dados" + NOVA_LINHA +
                "8 - Alterar senha" + NOVA_LINHA +
                "0 - Retornar ao Menu Inicial";
    }

    public String menuBillet() {
        return "---------------------------------" + NOVA_LINHA +
                "----Pagar Boleto----" + NOVA_LINHA;
    }

    public String menuAccount() {
        return "---------------------------------" + NOVA_LINHA +
                "----Atualizar dados----" + NOVA_LINHA +
                "1 - Limite" + NOVA_LINHA +
                "2 - Número" + NOVA_LINHA +
                "0 - Atualizar";
    }

}
