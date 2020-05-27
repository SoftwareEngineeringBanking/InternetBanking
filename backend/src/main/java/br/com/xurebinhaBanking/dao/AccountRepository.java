package br.com.xurebinhaBanking.dao;

import br.com.xurebinhaBanking.client.Client;
import br.com.xurebinhaBanking.config.H2JDBCUtils;

public class AccountRepository {
    private H2JDBCUtils conn;

    public AccountRepository(H2JDBCUtils conn) {
        this.conn = conn;
    }

    public void createAccount(Client client) {
        System.out.println(client.getName());
        String sql = "INSERT INTO client (name, cpf, password, second_password) " + "VALUES ('" + client.getName() + "', '" + client.getCpf() + "','" + client.getPassword() + "','" + client.getSecondPassword() + "')";
        //Todo: incluir conta
        conn.inserirRegistro(sql);

    }
}
