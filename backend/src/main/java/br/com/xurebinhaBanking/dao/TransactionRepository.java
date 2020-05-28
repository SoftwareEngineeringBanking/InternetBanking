package br.com.xurebinhaBanking.dao;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.model.TransactionType;

import java.math.BigDecimal;
import java.sql.Date;

public class TransactionRepository {
    private H2JDBCUtils conn;

    public TransactionRepository(H2JDBCUtils conn) {
        this.conn = conn;
    }

    public void createTransaction(TransactionType payment, int idAccountOut, int idAccountIn, BigDecimal value, Date today) {
        String sql = "INSERT INTO transaction (type_transaction, account_out, account_in, value_transfer, due_date ) " +
                "VALUES ('" + payment.toString() + "', " +
                idAccountOut + ", " +
                idAccountIn + "," +
                value + "," +
                today + "," + "')";
        conn.inserirRegistro(sql);
    }

}
