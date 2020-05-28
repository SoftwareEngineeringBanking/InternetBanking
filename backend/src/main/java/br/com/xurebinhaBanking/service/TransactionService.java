package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.dao.TransactionRepository;
import br.com.xurebinhaBanking.model.Invoice;
import br.com.xurebinhaBanking.model.TransactionType;

import java.sql.Date;
import java.util.Calendar;

public class TransactionService {

    private H2JDBCUtils conn;
    private TransactionRepository transactionRepository;

    public TransactionService(H2JDBCUtils conn){
        this.conn=conn;
        this.transactionRepository = new TransactionRepository(conn);
    }

    public void createPaymentTransaction(int idAccountOut, Invoice invoice) {
        Date today = (Date) Calendar.getInstance().getTime();
        transactionRepository.createTransaction(TransactionType.PAYMENT, idAccountOut,0,invoice.getValue(), today);
    }
}
