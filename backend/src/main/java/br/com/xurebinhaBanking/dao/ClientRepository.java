package br.com.xurebinhaBanking.dao;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        Long id = conn.insertRegisterAndGetId(sql);

        client.setId(id.intValue());
    }

    public String listClients() {

        ResultSet rs = conn.consultarRegistros("SELECT * FROM client");
        String result = "";
        while (true) {
            try {
                if (!rs.next()) break;

                result += "ID: " + rs.getString("id") + ", Nome: " + rs.getString("name") + ", CPF: " + rs.getString("cpf") + "\n";

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return result;
    }

    public boolean existClient(int selClient) {
        ResultSet rs = conn.consultarRegistros("SELECT (1) FROM client WHERE id= " + selClient);
        boolean existClient = false;
        try {
            if (rs.next()) {
                existClient = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return existClient;
    }

    public Client findClient(int selClient) {
        ResultSet rs = conn.consultarRegistros("SELECT * FROM client WHERE id= " + selClient);
        try {
            if (rs.next()) {
                return new Client(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("cpf"),
                        rs.getString("password"),
                        rs.getString("second_password"),
                        findAccounts(rs.getInt("id"))
                );
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private List<Account> findAccounts(int clientId) {
        List<Account> accountList = new ArrayList<>();

        ResultSet rs = conn.consultarRegistros("SELECT * FROM account WHERE client_id= " + clientId);
        while (true) {
            try {
                if (!rs.next()) break;

                accountList.add( new Account(rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("agency"),
                        rs.getInt("number_account"),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("limit_account"),
                        StatusAccount.valueOf(rs.getString("status_account")),
                        new AccountType(rs.getString("type_account")), //TODO buscar da lista do DB
                        new Bank(rs.getInt("bank"))));//TODO buscar da lista do DB

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return accountList;
    }

    public boolean passwordOk(int idClient, String password) {
        ResultSet rs = conn.consultarRegistros("SELECT (1) FROM client WHERE id= " + idClient + " AND password='" + password + "'");
        boolean passwordOk = false;
        try {
            if (rs.next()) {
                passwordOk = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return passwordOk;
    }
}
