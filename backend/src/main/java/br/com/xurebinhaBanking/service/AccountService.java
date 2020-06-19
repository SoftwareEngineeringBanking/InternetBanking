package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.model.account.Account;
import br.com.xurebinhaBanking.model.client.Client;
import br.com.xurebinhaBanking.model.invoice.Invoice;
import br.com.xurebinhaBanking.model.menu.Menu;
import br.com.xurebinhaBanking.repository.AccountRepository;
import br.com.xurebinhaBanking.repository.ClientRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class AccountService {

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

    public void actionAccount() {

        System.out.println("---------- ACESSO A CONTA ------------");
        System.out.println("-------- Clientes cadastrados: ----------");
        String listCLients = clientRepository.listClients();
        System.out.println(listCLients);
        System.out.println("Digite o ID do usuario que deseja:");

        int selClient = validateSelectClient(listCLients);
        validateFirstPassword(selClient);
        do {
            //find on
            Client client = clientRepository.findClient(selClient);
            System.out.println(Menu.menu());

            System.out.println("Digite sua Opção:");
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

    private int validateSelectClient(String listCLients) {
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
        return selClient;
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

    private void validateSecondPassword(Client client) {
        boolean validaSenhaCliente = false;
        System.out.println("Digite a SEGUNDA SENHA do usuario:");
        String secondPassClient = in.next();

        do {
            validaSenhaCliente = clientRepository.secondPasswordOk(client.getId(), secondPassClient);
            if (!validaSenhaCliente) {
                System.out.println("Segunda Senha do cliente '" + client.getId() + "' nao confere, tente novamente:");
                secondPassClient = in.next();
                //todo ajustar para sair, caso queira
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
        System.out.println(listCLients);
        int selClientDest = validateSelectClient(listCLients);
        Client clientDest = clientRepository.findClient(selClientDest);
        Account selAccountIn = selectAccount(clientDest);
        //todo

        System.out.println("Digite o valor de transferencia:");
        String valor = in.next();
        valor.replace(".", ",");
        BigDecimal valueToTransfer = new BigDecimal(valor);

        if (getAllFunds(selAccountOut).compareTo(valueToTransfer) >= 1) {
            transactionService.createTransferTransaction(selAccountOut.getId(), selAccountIn.getId(), valueToTransfer);

            //debitar valor da conta
            selAccountOut.setBalance(selAccountOut.getBalance().subtract(valueToTransfer));
            accountRepository.updateBalance(selAccountOut);
            //aumentar na conta destino
            selAccountIn.setBalance(selAccountIn.getBalance().add(valueToTransfer));
            accountRepository.updateBalance(selAccountIn);

            System.out.println("Valor transferido com sucesso!");
            System.out.println("Conta Origem:   " + selAccountOut.getAgency()+"/"+selAccountOut.getNumber());
            System.out.println(" Valor: R$" + valueToTransfer);
            System.out.println("Saldo atual da conta: R$ " + selAccountOut.getBalance());
            System.out.println("-------------------------------");

        } else {
            System.out.println("O cliente " + client.getName() + " não possui saldo suficiente!");
        }

    }

    private void makeLoan(Client client){
        boolean fimMenuMakeLoan = false;

        System.out.println("-----FAZER EMPRESTIMO-----");
        System.out.println("-----VENCIMENTO TODO DIA 1º-----");
        Account account = selectAccount(client);
        float loanLimit = account.getLimitAccount().floatValue();

        do {
            System.out.println("1 - SOLICITAR EMPRESTIMO");
            System.out.println("0 - VOLTAR");
            System.out.println("Digite sua Opção:");
            int acao = in.nextInt();

            switch (acao) {
                case 1:
                    if (loanLimit > 0) {
                        float loanAmount;

                        do {
                            System.out.println("VALOR DISPONIVEL PARA EMPRÉSTIMO: R$ " + account.getLimitAccount());
                            loanAmount = in.nextFloat();
                        } while ((loanAmount > loanLimit) || (loanAmount < 1));

                        int parcelas;
                        do {
                            System.out.println("OPCOES DE EMPRESTIMO PARA O VALOR R$ " + loanAmount);
                            for (int i=1; i<4; i++){
                                System.out.println(i + " - " + i + "x R$" + loanAmount/i);
                            }
                            System.out.println("0 - VOLTAR");

                            System.out.println("SELECIONE UMA OPCAO");
                            parcelas = in.nextInt();
                        }while(parcelas < 1 || parcelas > 3);

                        float valParcelas = loanAmount / parcelas;
                        System.out.println("Valor selecionado: " + parcelas + "x de R$ " + valParcelas);

                        System.out.println(client.getName() + " DIGITE SUA SEGUNDA SENHA PARA CONFIRMAR: ");
                        String secondPasswordClient = in.next();
                        boolean validaSegundaSenhaCliente = false;
                        do {
                            validaSegundaSenhaCliente = clientRepository.secondPasswordOk(client.getId(), secondPasswordClient);
                            if (!validaSegundaSenhaCliente) {
                                System.out.println("Senha incorreta, tente novamente:");
                                secondPasswordClient = in.next();
                            }
                        } while (!validaSegundaSenhaCliente);

                        String loanAmountReplace = String.valueOf(loanAmount);
                        loanAmountReplace.replace(".",",");
                        BigDecimal valorEmprestimoSelected = new BigDecimal(loanAmountReplace);

                        String valParcelasReplace = String.valueOf(valParcelas);
                        valParcelasReplace.replace(".",",");
                        BigDecimal valorParcelasSelected = new BigDecimal(loanAmountReplace);

                        account.setBalance(account.getBalance().add(valorEmprestimoSelected));
                        accountRepository.updateBalance(account);
                        account.setLimitAccount(account.getLimitAccount().subtract(valorEmprestimoSelected));
                        accountRepository.updateLimit(account);


//                        Calendar c = Calendar.getInstance();
//                        System.out.println("Data e Hora atual: "+c.getTime());

                        for(int i=1; i<=parcelas; i++){

                            //gerar a data
                            transactionService.createLoanTransaction(account.getId(), valorParcelasSelected /*,data*/);
                        }


                        System.out.println("Saldo atual da conta: R$ "+account.getBalance());
                        System.out.println("Limite atual da conta: R$ "+account.getLimitAccount());
                        System.out.println("-------------------------------");

                    } else {
                        System.out.println("CLIENTE NÃO POSSUI LIMITE PARA EMPRÉSTIMO");
                        System.out.println("0 - VOLTAR");
                        int opcao = in.nextInt();
                        switch (opcao){
                            default:
                                fimMenuMakeLoan = true;
                        }
                    }
                    break;
                default:
                    fimMenuMakeLoan = true;
                    break;
            }
        } while (!fimMenuMakeLoan);
    }

    //- Pagamento de contas (solicitando uma segunda senha e um código de barras válido em algum formato específico a escolher pelo grupo)
    //o valor é debitado da conta e a operação aparece no extrato
    private void payBills(Client client) {
        System.out.println("Para o pagamento de contas, forneca sua segunda senha.");
        validateSecondPassword(client);

        Account selAccount = selectAccount(client);

        System.out.println("Digite um codigo de barras valido:");
        System.out.println("(00001190620200000000000010)");
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
            selAccount.setBalance(selAccount.getBalance().subtract(invoice.getValue()));

            //Atualizar o saldo
            accountRepository.updateBalance(selAccount);
            System.out.println("Conta Paga com sucesso!");
            System.out.println("Codigo:   " + invoice.getId());
            System.out.println(" Valor: R$" + invoice.getValue());
            System.out.println("Saldo atual da conta: R$ " + selAccount.getBalance());
            System.out.println("-------------------------------");

        } else {
            System.out.println("O cliente " + client.getName() + " não possui saldo suficiente!");
        }
    }

    public Account selectAccount(Client client) {
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
                return null;
                //todo ajustar para sair, caso queira
            }
        } while (!validaSelecaoAccount);

        for (int i = 0; i < client.getAccountList().size(); i++) {
            if (client.getAccountList().get(i).getId() == selAccount)
                return client.getAccountList().get(i);
        }

        return null;
    }

    public BigDecimal getAllFunds(Account account) {
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
        if (!client.getAccountList().isEmpty()) {
            Account selAccountOut = selectAccount(client);

            do {
                System.out.println(Menu.menuAccount());
                System.out.println("Digite sua opcao:");
                int option = in.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Digite o limite desejado:");
                        selAccountOut.setLimitAccount(in.nextBigDecimal());
                        accountRepository.updateLimit(selAccountOut);
                        break;
                    case 2:
                        System.out.println("Digite o novo número da conta:");
                        selAccountOut.setNumber(in.nextInt());
                        accountRepository.updateNumber(selAccountOut);
                        break;
                    case 0:
                    default:
                        finalize = true;
                        break;
                }
            } while (!finalize);
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
        Scanner in = new Scanner(System.in);
        Account selAccount = selectAccount(client);

        System.out.println("Digite o valor do depósito:");
        String valor = in.next();
        valor.replace(".", ",");
        BigDecimal valueToDeposit = new BigDecimal(valor);

        selAccount.setBalance(selAccount.getBalance().add(valueToDeposit));

        accountRepository.updateBalance(selAccount);
        transactionService.createDepositTransaction(selAccount.getId(), valueToDeposit);
        System.out.println("Saldo atual da conta: R$ " + selAccount.getBalance());
        System.out.println("-------------------------------");
    }
}
