package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.dao.AccountRepository;
import br.com.xurebinhaBanking.model.Client;

import java.util.Scanner;

public class AccountService {

    private static String NOVA_LINHA = "\n";
    private H2JDBCUtils conn;
    private AccountRepository accountRepository;

    public AccountService(H2JDBCUtils conn) {
        this.conn = conn;
        this.accountRepository = new AccountRepository(conn);
    }

    public void actionAccount(){
        Scanner in = new Scanner(System.in);
        Client client = new Client();
        boolean sairConta = false;

        System.out.println("------------ MENU DE ACESSO A CONTA ------------");
        System.out.println("Digite seu cpf:");
        //TODO Ajustar a busca de CPF
        client.setCpf(in.next());

        System.out.println("Digite sua Senha:");
        //TODO ajustar a validacao de senha
        client.setPassword(in.next());

        do {
            System.out.println(menu());

            System.out.println("Digite sua opcao:");
            int acao = in.nextInt();

            switch (acao) {
                case 1:
                    System.out.println("Fazer Transferencia" + NOVA_LINHA);

                    System.out.println("Valor:");
                    client.setBalance(in.nextBigDecimal());
                    break;
                case 2:
                    System.out.println("Verificar Saldo" + NOVA_LINHA);
                    System.out.println("Saldo Atual:"+client.getBalance());
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
                    sairConta =true;
                    break;
            }
        } while(!sairConta);
    }
    private static String menu() {
        return "1 - Fazer Transferencia" +NOVA_LINHA+
                "2 - Verificar Saldo" +NOVA_LINHA+
                "3 - Realizar Saque"+NOVA_LINHA+
                "0 - Retornar ao Menu Inicial";
    }
}
