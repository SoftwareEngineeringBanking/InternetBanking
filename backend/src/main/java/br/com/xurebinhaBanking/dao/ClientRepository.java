package br.com.xurebinhaBanking.dao;

import br.com.xurebinhaBanking.model.Client;
import br.com.xurebinhaBanking.config.H2JDBCUtils;

public class ClientRepository {
    private H2JDBCUtils conn;

    public ClientRepository(H2JDBCUtils conn) {
        this.conn = conn;
    }

    public void createClient(Client client) {
        String sql = "INSERT INTO client (name, cpf, password, second_password) " + "VALUES ('" +
                client.getName() + "', '" +
                client.getCpf() + "','" +
                client.getPassword() + "','" +
                client.getSecondPassword() + "')";
        
        conn.inserirRegistro(sql);
    }
}
