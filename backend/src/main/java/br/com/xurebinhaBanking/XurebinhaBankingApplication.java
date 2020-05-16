package br.com.xurebinhaBanking;

import java.util.Scanner;

public class XurebinhaBankingApplication {
    private static String NOVA_LINHA = "\n";

    public static void main(String[] args) {

        boolean fimSistema = true;
        do {
            System.out.println(imprimeMenu());
            Scanner in = new Scanner(System.in);
            int opcao = in.nextInt();

            switch (opcao){
                case 1:
                    System.out.println("Opcao 1"+NOVA_LINHA);
                    break;
                case 0:
                default:
                    System.out.println("Opcao 0"+NOVA_LINHA);
                    fimSistema =false;


            }

        } while (fimSistema);

    }

    private static String imprimeMenu() {
        return "1 - Inicio" +NOVA_LINHA+
                "0 - Sair sistema"+NOVA_LINHA;
    }

}
