package br.com.xurebinhaBanking;

import br.com.xurebinhaBanking.account.*;

import java.util.Scanner;

public class XurebinhaBankingApplication {
    private static String NOVA_LINHA = "\n";

    public static void main(String[] args) {

        CreateAccount createAccount = new CreateAccount();
        AccessAccount accessAccount = new AccessAccount();

        boolean fimSistema = true;

        do {
            System.out.println(imprimeMenu());
            Scanner in = new Scanner(System.in);
            System.out.println("Digite aqui:");
            int opcao = in.nextInt();

            switch (opcao){
                case 1:
                    System.out.println("Criar Conta"+NOVA_LINHA);

                    System.out.println(createAccount.create());
                    break;
                case 2:
                    accessAccount.actionAccount();
                    break;
                case 0:
                default:
                    System.out.println("Opcao 0"+NOVA_LINHA);
                    fimSistema =false;
            }

        } while (fimSistema);

    }

    private static String imprimeMenu() {
        return "1 - Criar Conta" +NOVA_LINHA+
                "2 - Acessar Conta" +NOVA_LINHA+
                "0 - Sair sistema"+NOVA_LINHA;
    }

}
