package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.dao.AccountRepository;
import br.com.xurebinhaBanking.dao.ClientRepository;
import br.com.xurebinhaBanking.model.Account;
import br.com.xurebinhaBanking.model.Client;

import java.util.Scanner;

public class AccountService {

    private static String NOVA_LINHA = "\n";
    private H2JDBCUtils conn;
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private static boolean FIM_MENU_CONTA = false;

    public AccountService(H2JDBCUtils conn) {
        this.conn = conn;
        this.accountRepository = new AccountRepository(conn);
        this.clientRepository = new ClientRepository(conn);
    }

    public void actionAccount(){
        Scanner in = new Scanner(System.in);

        System.out.println("---------- ACESSO A CONTA ------------");
        System.out.println("-------- Clientes cadastrados: ----------");
        String listCLients = clientRepository.listClients();
        System.out.println(listCLients);
        System.out.println("Digite o ID do usuario que deseja:");

        boolean validaSelecaoCliente = false;
        int selClient = in.nextInt();
        do{
            validaSelecaoCliente = clientRepository.existClient(selClient);
            if(!validaSelecaoCliente){
                System.out.println("Cliente nao encontrado, selecione outro:");
                System.out.println(listCLients);
                selClient = in.nextInt();
            }
        }while(!validaSelecaoCliente);

        boolean validaSenhaCliente = false;
        System.out.println("Digite a Senha do usuario:");
        String senhaClient = in.next();
        do{
            validaSenhaCliente = clientRepository.passwordOk(selClient, senhaClient);
            if(!validaSelecaoCliente){
                System.out.println("Senha do cliente '"+selClient+"' nao confere, tente novamente:");
                System.out.println(listCLients);
                senhaClient = in.next();
            }
        }while(!validaSenhaCliente);
        //find on
        Client client = clientRepository.findClient(selClient);
        do {
            System.out.println(menu());

            System.out.println("Digite sua opcao:");
            int acao = in.nextInt();

            switch (acao) {
                case 1:
                    System.out.println("Fazer Transferencia" + NOVA_LINHA);

                    System.out.println("Valor:");
                    //client.get(in.nextBigDecimal());
                    break;
                case 2:
                    viewBalance(client);
                    //System.out.println("Saldo Atual:"+client.getBalance());
                    break;
                case 3:
                    //TODO ajustar
                    System.out.println("Realizar Saque" + NOVA_LINHA);

                    System.out.println("Valor:");
                   /* double saque = in.nextDouble();

                    if (saque > client.getBalance()) {
                        System.out.println("Saldo Insuficiente" + NOVA_LINHA);
                    } else {
                        client.setBalance(client.getBalance() - saque);

                        System.out.println("Saldo Atual:" + client.getBalance());
                    }*/
                    break;
                case 0:
                default:
                    FIM_MENU_CONTA = true;
                    break;
            }
        } while(!FIM_MENU_CONTA);
    }

    private void viewBalance(Client client) {
        if(client.getAccountList().size()>0) {

            System.out.println("CLIENTE: "+client.getName() + " CPF: "+client.getCpf());
            for (int i = 0; i < client.getAccountList().size(); i++) {
                Account acc = client.getAccountList().get(i);
                System.out.println("BANCO: "+acc.getBank().getCod()+" - "+acc.getBank().getName());
                System.out.println("AGENCIA : "+acc.getAgency()+ " NUM. CONTA: "+acc.getNumber());
                System.out.println("STATUS: "+acc.getStatusAccount().toString());
                System.out.println("TIPO DE CONTA: "+acc.getAccountType().getNameAccountType());
                System.out.println("-------------------");
                System.out.println("SALDO: "+acc.getBalance() + "  LIMITE: "+acc.getLimitAccount());
            }
        }else{
            System.out.println("CLIENTE: "+client.getName() + " não possui contas.");
        }
    }

    private static String menu() {
        return "---------------------------------" +NOVA_LINHA+
                "----MENU DE CONTA DO CLIENTE----"+NOVA_LINHA+
                "---------------------------------" +NOVA_LINHA+
                "1 - Fazer Transferencia" +NOVA_LINHA+
                "2 - Verificar Saldo" +NOVA_LINHA+
                "3 - Realizar Saque"+NOVA_LINHA+
                "4 - Informacoes de conta"+NOVA_LINHA+
                "0 - Retornar ao Menu Inicial";
    }
}
