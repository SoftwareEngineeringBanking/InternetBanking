package br.com.xurebinhaBanking;

import br.com.xurebinhaBanking.account.Account;
import br.com.xurebinhaBanking.config.CreateTablesAndDefaultRegisters;
import br.com.xurebinhaBanking.config.H2JDBCUtils;

import java.util.Scanner;

public class XurebinhaBankingApplication {
    private static String NOVA_LINHA = "\n";
    private static H2JDBCUtils conn;

    public static void main(String[] args){

        conn = iniciaConexao();
        inicializaTabelas();
        Account account = new Account(conn);

        boolean fimSistema = false;

        do {
            System.out.println(imprimeMenu());
            Scanner in = new Scanner(System.in);
            System.out.println("Digite sua opcao:");
            int opcao = in.nextInt();

            switch (opcao) {
                case 1:
                    account.createAccount();
                    break;
                case 2:
                    account.actionAccount();
                    break;
                case 0:
                default:
                    fimSistema = true;
            }
        } while (!fimSistema);
        conn.fecharConexao();
    }

    private static String imprimeMenu() {
        return "1 - Criar uma Conta" + NOVA_LINHA +
                "2 - Acessar uma Conta" + NOVA_LINHA +
                "0 - Finalizar o sistema" + NOVA_LINHA;
    }

    private static H2JDBCUtils iniciaConexao() {
        return new H2JDBCUtils();
    }

    private static void inicializaTabelas() {
        new CreateTablesAndDefaultRegisters(conn);
    }

}
