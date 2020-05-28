package br.com.xurebinhaBanking.repository;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.model.account.Account;
import br.com.xurebinhaBanking.model.account.AccountType;
import br.com.xurebinhaBanking.model.bank.Bank;
import br.com.xurebinhaBanking.model.account.StatusAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {
    private H2JDBCUtils conn;

    public AccountRepository(H2JDBCUtils conn) {
        this.conn = conn;
    }

    public void createAccount(Account account) {
        String sql = "INSERT INTO account (client_id, agency, number_account, type_account, balance, bank, limit_account, status_account) " +
                "VALUES (" + account.getClientId() + ", " +
                account.getAgency() + ", " +
                account.getNumber() + ",'" +
                account.getAccountType().getNameAccountType() + "'," +
                account.getBalance() + "," +
                account.getBank().getCod() + "," +
                account.getLimitAccount() + ",'" +
                account.getStatusAccount().toString() + "')";
        conn.inserirRegistro(sql);
    }

    public String listAccounts(int clientId) {

        ResultSet rs = conn.consultarRegistros("SELECT * FROM account WHERE client_id=" + clientId);
        String result = "";
        while (true) {
            try {
                if (!rs.next()) break;

                result += "COD:" + rs.getInt("id") + " BCO:" + rs.getString("bank") + " AG: " + rs.getInt("agency") + " C/C: "
                        + rs.getInt("number_account") + " - " + rs.getString("type_account") + "\n";

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return result;
    }

    public boolean existAccount(int selAccount) {
        ResultSet rs = conn.consultarRegistros("SELECT (1) FROM account WHERE id= " + selAccount);
        boolean existAccount = false;
        try {
            if (rs.next()) {
                existAccount = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return existAccount;
    }

    public Account findAccount(int selAccount) {
        ResultSet rs = conn.consultarRegistros("SELECT * FROM client WHERE id= " + selAccount);
        try {
            if (rs.next()) {
                new Account(rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getInt("agency"),
                        rs.getInt("number_account"),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("limit_account"),
                        StatusAccount.valueOf(rs.getString("status_account")),
                        new AccountType(rs.getString("type_account")), //TODO buscar da lista do DB
                        new Bank(rs.getInt("bank")));//TODO buscar da lista do DB
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateBalance(Account account){
        conn.update("UPDATE account SET balance = " +account.getBalance()+ " WHERE id= " + account.getId());
    }

    public void updateAccount(Account account) {
        conn.update("UPDATE account SET limit_account = " +account.getLimitAccount()+ "," +
                " status_account = " +account.getStatusAccount()+ " WHERE id= " + account.getId());
    }
}
