package br.com.xurebinhaBanking;

import br.com.xurebinhaBanking.config.CreateTablesAndDefaultRegisters;
import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.service.AccountService;
import br.com.xurebinhaBanking.service.ClientService;

import java.util.Scanner;

public class XurebinhaBankingApplication {
    private static String NOVA_LINHA = "\n";
    private static H2JDBCUtils conn;
    private static AccountService accountService;
    private static ClientService clientService;
    private static boolean FIM_SISTEMA = false;

    public static void main(String[] args){

        iniciaConexao();
        iniciaServices();

        do {
            System.out.println(imprimeMenu());
            Scanner in = new Scanner(System.in);
            System.out.println("Digite sua opcao:");
            int opcao = in.nextInt();

            switch (opcao) {
                case 1:
                    clientService.createClientAccount();
                    break;
                case 2:
                    accountService.actionAccount();
                    break;
                case 0:
                default:
                    FIM_SISTEMA = true;
            }
        } while (!FIM_SISTEMA);
        conn.fecharConexao();
    }

    private static void iniciaServices() {
        accountService = new AccountService(conn);
        clientService  = new ClientService(conn);
    }

    private static String imprimeMenu() {
        return "1 - Criar uma Conta" + NOVA_LINHA +
                "2 - Acessar uma Conta" + NOVA_LINHA +
                "0 - Finalizar o sistema" + NOVA_LINHA;
    }

    private static void iniciaConexao() {
        conn = new H2JDBCUtils();
        inicializaTabelas();
    }

    private static void inicializaTabelas() {
        new CreateTablesAndDefaultRegisters(conn);
    }

}
