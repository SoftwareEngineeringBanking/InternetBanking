package br.com.xurebinhaBanking.dao;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.model.Account;

public class AccountRepository {
    private H2JDBCUtils conn;

    public AccountRepository(H2JDBCUtils conn) {
        this.conn = conn;
    }

    public void createAccount(Account account) {
        String sql = "INSERT INTO account (agency, numberAccount, type_account, balance, bank, limit_account,status_account) " +
                "VALUES ('" + account.getAgency() + "', '" +
                account.getNumber() + "','" +
                account.getAccountType().toString() + "','" +
                account.getBalance() + "','" +
                account.getBank().getCod() + "','" +
                account.getLimitAccount() + "','" +
                account.getStatusAccount().toString() + "')";
        conn.inserirRegistro(sql);
    }
}
