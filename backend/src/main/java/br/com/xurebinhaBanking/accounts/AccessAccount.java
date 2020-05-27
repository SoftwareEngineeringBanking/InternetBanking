package br.com.xurebinhaBanking.accounts;

import java.util.Scanner;

public class AccessAccount {

    private static String NOVA_LINHA = "\n";

    Scanner in = new Scanner(System.in);

    Account account = new Account();

    public void actionAccount(){

        System.out.println("Digite seu Login:");
        account.setLogin(in.next());

        System.out.println("Digite sua Senha:");
        account.setPassword(in.next());

        boolean finalize = true;

        do {
            System.out.println(menu());

            System.out.println("Digite aqui:");
            int acao = in.nextInt();

            switch (acao) {
                case 1:
                    System.out.println("Fazer Deposito" + NOVA_LINHA);

                    System.out.println("Valor:");
                    account.setBalance(in.nextDouble());
                    break;
                case 2:
                    System.out.println("Verificar Saldo" + NOVA_LINHA);
                    System.out.println("Saldo Atual:"+account.getBalance());
                    break;
                case 3:
                    System.out.println("Realizar Saque" + NOVA_LINHA);

                    System.out.println("Valor:");
                    double saque = in.nextDouble();

                    if (saque > account.getBalance()) {
                        System.out.println("Saldo Insuficiente" + NOVA_LINHA);
                    } else {
                        account.setBalance(account.getBalance() - saque);

                        System.out.println("Saldo Atual:" + account.getBalance());
                    }
                    break;
                case 4 :
                    System.out.println("Retornar ao Menu Inicial" + NOVA_LINHA);
                    //To do
                    break;
                default:
                    System.out.println("Finalizar"+NOVA_LINHA);
                    finalize =false;
            }
        } while(finalize);
    }
    private static String menu() {
        return "1 - Fazer Deposito" +NOVA_LINHA+
                "2 - Verificar Saldo" +NOVA_LINHA+
                "3 - Realizar Saque"+NOVA_LINHA;
    }
}
