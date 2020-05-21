package br.com.xurebinhaBanking.account;

import java.util.Scanner;

public class CreateAccount {

    Scanner in = new Scanner(System.in);

    Account account = new Account();

    public String create() {
        System.out.println("Digite seu Login:");
        account.setLogin(in.next());

        System.out.println("Digite sua Senha:");
        account.setPassword(in.next());

        System.out.println("Digite seu Nome:");
        account.setName(in.next());

        System.out.println("Digite seu Cpf:");
        account.setCpf(in.next());

       return  ("Bem vindo!"+account.getName());
    }
}
