package br.com.xurebinhaBanking.account;

import br.com.xurebinhaBanking.client.Client;
import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.dao.AccountRepository;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Scanner;

@Data
public class Account {
    private int agency;
    private int number;
    private double balance;
    private BigDecimal limitAccount;
    private StatusAccount statusAccount;
    private static String NOVA_LINHA = "\n";
    private H2JDBCUtils conn;
    private AccountRepository accountRepository;

    public Account(H2JDBCUtils conn){
        this.conn = conn;
        this.accountRepository = new AccountRepository(conn);
    }

    public String createAccount() {
        Scanner in = new Scanner(System.in);
        Client client = new Client();
        System.out.println("------------ MENU DE CRIACAO DE CONTA ------------");
        System.out.println("Digite seu nome:");
        client.setName(in.next());

        System.out.println("Digite seu CPF:");
        client.setCpf(in.next());

        System.out.println("Digite sua senha:");
        client.setPassword(in.next());

        System.out.println("Digite sua segunda senha:");
        client.setSecondPassword(in.next());

        accountRepository.createAccount(client);

        return  ("Bem vindo, "+client.getName()+"!"+NOVA_LINHA);
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
                    System.out.println("Fazer Deposito" + NOVA_LINHA);

                    System.out.println("Valor:");
                    client.setBalance(in.nextDouble());
                    break;
                case 2:
                    System.out.println("Verificar Saldo" + NOVA_LINHA);
                    System.out.println("Saldo Atual:"+client.getBalance());
                    break;
                case 3:
                    //TODO ajustar
                    System.out.println("Realizar Saque" + NOVA_LINHA);

                    System.out.println("Valor:");
                    double saque = in.nextDouble();

                    if (saque > client.getBalance()) {
                        System.out.println("Saldo Insuficiente" + NOVA_LINHA);
                    } else {
                        client.setBalance(client.getBalance() - saque);

                        System.out.println("Saldo Atual:" + client.getBalance());
                    }
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