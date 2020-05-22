package br.com.xurebinhaBanking.account;

import br.com.xurebinhaBanking.config.H2JDBCUtils;

import java.sql.Connection;
import java.util.Scanner;

public class CreateAccount {

    Scanner in = new Scanner(System.in);

    Account account = new Account();

    public String create(H2JDBCUtils conn) {
        System.out.println("Digite seu Login:");
        account.setLogin(in.next());

        System.out.println("Digite sua Senha:");
        account.setPassword(in.next());

        System.out.println("Digite seu Nome:");
        account.setName(in.next());

        System.out.println("Digite seu Cpf:");
        account.setCpf(in.next());

        createAccountInDb(account, conn);

       return  ("Bem vindo! "+account.getName());
    }

    private void createAccountInDb(Account acc, H2JDBCUtils conn){
        String sql = "INSERT INTO account (login, senha, nome, cpf) " + "VALUES ('"+acc.getLogin()+"','"+acc.getPassword()+"','"+acc.getName()+"','"+acc.getCpf()+"')";
        conn.inserirRegistro(sql);
    }
}
