package br.com.xurebinhaBanking;

import br.com.xurebinhaBanking.accounts.*;
import br.com.xurebinhaBanking.config.CreateTablesAndDefaultRegisters;
import br.com.xurebinhaBanking.config.H2JDBCUtils;

import java.sql.SQLException;
import java.util.Scanner;

public class XurebinhaBankingApplication {
    private static String NOVA_LINHA = "\n";
    private static H2JDBCUtils conn;

    public static void main(String[] args) throws SQLException {

        CreateAccount createAccount = new CreateAccount();
        AccessAccount accessAccount = new AccessAccount();
        conn = iniciaConexao();
        inicializaTabelas();

        boolean fimSistema = false;

        do {
            System.out.println(imprimeMenu());
            Scanner in = new Scanner(System.in);
            System.out.println("Digite aqui:");
            int opcao = in.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Criar Conta" + NOVA_LINHA);

                    System.out.println(createAccount.create(conn));
                    break;
                case 2:
                    accessAccount.actionAccount();
                    break;
                case 0:
                default:
                    System.out.println("Opcao 0" + NOVA_LINHA);
                    fimSistema = true;
            }

        } while (!fimSistema);

        conn.fecharConexao();

    }

    private static String imprimeMenu() {
        return "1 - Criar Conta" + NOVA_LINHA +
                "2 - Acessar Conta" + NOVA_LINHA +
                "0 - Sair sistema" + NOVA_LINHA;
    }

    private static H2JDBCUtils iniciaConexao() {
        return new H2JDBCUtils();
    }

    private static void inicializaTabelas() {
        new CreateTablesAndDefaultRegisters(conn);
    }

}
