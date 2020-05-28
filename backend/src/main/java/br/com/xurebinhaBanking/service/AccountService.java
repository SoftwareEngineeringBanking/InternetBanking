package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.dao.AccountRepository;
import br.com.xurebinhaBanking.dao.ClientRepository;
import br.com.xurebinhaBanking.model.Account;
import br.com.xurebinhaBanking.model.Client;
import br.com.xurebinhaBanking.model.Invoice;
import br.com.xurebinhaBanking.model.TransactionType;

import java.math.BigDecimal;
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

        boolean validaSenhaCliente = false;
        System.out.println("Digite a Senha do usuario:");
        String senhaClient = in.next();
        do {
            validaSenhaCliente = clientRepository.passwordOk(selClient, senhaClient);
            if (!validaSenhaCliente) {
                System.out.println("Senha do cliente '" + selClient + "' nao confere, tente novamente:");
                System.out.println(listCLients);
                senhaClient = in.next();

            }
        } while (!validaSenhaCliente);
        //find on
        Client client = clientRepository.findClient(selClient);
        do {
            System.out.println(menu());

            System.out.println("Digite sua opcao:");
            int acao = in.nextInt();

            switch (acao) {
                case 1:
                    System.out.println("Fazer Transferencia" + NOVA_LINHA);
                    break;
                case 2:
                    viewBalance(client);
                    break;
                case 3:
                    payBills(client);
                    break;
                case 4:
                    //TODO ajustar
                    System.out.println("Funcao ainda nao implementada!");
                    break;
                case 5:
                    changePassword(client);
                    break;
                case 0:
                default:
                    FIM_MENU_CONTA = true;
                    break;
            }
        } while (!FIM_MENU_CONTA);
    }

    //- Pagamento de contas (solicitando uma segunda senha e um código de barras válido em algum formato específico a escolher pelo grupo)
    //o valor é debitado da conta e a operação aparece no extrato
    private void payBills(Client client) {
        System.out.println("Para o pagamento de contas, forneca sua segunda senha.");
        System.out.println("Digite a Senha do usuario:");
        boolean validaSenhaCliente = false;
        String secondPassClient = in.next();
        do {
            validaSenhaCliente = clientRepository.secondPasswordOk(client.getId(), secondPassClient);
            if (!validaSenhaCliente) {
                System.out.println("Segunda Senha do cliente '" + client.getId() + "' nao confere, tente novamente:");
                secondPassClient = in.next();
                //todo ajustar para sair, caso queira
            }
        } while (!validaSenhaCliente);

        //todo ajustar para selecionar a conta
        Account selAccount = selectAccount(client);

        System.out.println("Digite um codigo de barras valido:");
        String codigoBarras = in.next();

        boolean validaCodigoBarras = false;
        do {
            validaCodigoBarras = checkCode(codigoBarras);
            if (!validaCodigoBarras) {
                System.out.println("Codigo de barras '" + codigoBarras + "' nao é válido, tente novamente:");
                codigoBarras = in.next();
                //todo ajustar para sair, caso queira
            }
        } while (!validaCodigoBarras);

        Invoice invoice = new Invoice(codigoBarras);
        if(getAllFunds(selAccount).compareTo(invoice.getValue())>=1){
            //adicionar pagamento nas transacoes
            transactionService.createPaymentTransaction(selAccount.getId(), invoice);
            
            //debitar valor da conta
            selAccount.getBalance().subtract(invoice.getValue());

            //Atualizar o saldo
            accountRepository.updateBalance(selAccount);

        }else{
            System.out.println("O cliente "+client.getName()+" não possui saldo suficiente!");
        }
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

        for (int i = 0; i <client.getAccountList().size(); i++) {
            if(client.getAccountList().get(i).getId() == selAccount)
                return client.getAccountList().get(i);
        }

        return null;
    }

    private BigDecimal getAllFunds(Account account) {
        return account.getLimitAccount().add(account.getBalance());
    }

    private boolean checkCode(String codigoBarras) {
        return codigoBarras.length()==26;
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

    private void changePassword(Client client) {
        Scanner in = new Scanner(System.in);
        System.out.println(client.getName() + " DIGITE SUA SENHA ATUAL: ");
        String passwordClient = in.next();
        boolean validaSenhaCliente = false;

        do{
            validaSenhaCliente = clientRepository.passwordOk(client.getId(), passwordClient);
            if(!validaSenhaCliente){
                System.out.println("Senha incorreta, tente novamente:");
                passwordClient = in.next();
            }
        }while(!validaSenhaCliente);

        System.out.println(client.getName() + " DIGITE SUA SEGUNDA SENHA ATUAL: ");
        String secondPasswordClient = in.next();
        boolean validaSegundaSenhaCliente = false;
        do{
            validaSegundaSenhaCliente = clientRepository.secondPasswordOk(client.getId(), secondPasswordClient);
            if(!validaSegundaSenhaCliente){
                System.out.println("Senha incorreta, tente novamente:");
                secondPasswordClient = in.next();
            }
        }while(!validaSegundaSenhaCliente);

        System.out.println("DIGITE SUA NOVA SENHA");
        String newPasswordClient = in.next();

        System.out.println("DIGITE SUA NOVA SEGUNDA SENHA");
        String newSecondPasswordClient = in.next();

        client.setPassword(newPasswordClient);
        client.setSecondPassword(newSecondPasswordClient);

        clientRepository.changePasswordBd(client);
    }

    private static String menu() {
        return "---------------------------------" +NOVA_LINHA+
                "----MENU DE CONTA DO CLIENTE----"+NOVA_LINHA+
                "---------------------------------" +NOVA_LINHA+
                "1 - Fazer Transferencia" +NOVA_LINHA+
                "2 - Verificar Saldo" +NOVA_LINHA+
                "3 - Realizar Saque"+NOVA_LINHA+
                "4 - Informacoes de conta"+NOVA_LINHA+
                "5 - Alterar senha"+NOVA_LINHA+
                "0 - Retornar ao Menu Inicial";
    }

}
