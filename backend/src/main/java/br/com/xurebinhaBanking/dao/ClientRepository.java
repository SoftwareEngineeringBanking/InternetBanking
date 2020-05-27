package br.com.xurebinhaBanking.dao;

import br.com.xurebinhaBanking.model.Client;
import br.com.xurebinhaBanking.config.H2JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public String listClients(){

        ResultSet rs = conn.consultarRegistros("SELECT * FROM client");
        String result = "";
        while(true){
            try {
                if (!rs.next()) break;

                result += "ID: " +rs.getString("id") +", Nome: " +rs.getString("name") + ", CPF: "+rs.getString("cpf") + "\n";

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return result;
    }
}
