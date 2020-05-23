package br.com.xurebinhaBanking.account;

import br.com.xurebinhaBanking.client.Client;
import br.com.xurebinhaBanking.config.H2JDBCUtils;

import java.util.Scanner;

public class CreateAccount {

    Scanner in = new Scanner(System.in);

    Client client = new Client();

    public String create(H2JDBCUtils conn) {
        System.out.println("Digite seu nome:");
        client.setName(in.next());

        System.out.println("Digite seu CPF:");
        client.setCpf(in.next());

        System.out.println("Digite sua senha:");
        client.setPassword(in.next());

        System.out.println("Digite sua segunda senha:");
        client.setSecondPassword(in.next());

        createAccountInDb(client, conn);

       return  ("Bem vindo! "+client.getName());
    }

    private void createAccountInDb(Client acc, H2JDBCUtils conn){
        System.out.println(acc.getName());
        String sql = "INSERT INTO client (name, cpf, password, second_password) " + "VALUES ('"+acc.getName()+"', '"+acc.getCpf()+"','"+acc.getPassword()+"','"+acc.getSecondPassword()+"')";
        conn.inserirRegistro(sql);
    }
}
