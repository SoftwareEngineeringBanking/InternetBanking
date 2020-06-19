package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.model.invoice.Invoice;
import br.com.xurebinhaBanking.model.transaction.TransactionType;
import br.com.xurebinhaBanking.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

public class TransactionService {

    private H2JDBCUtils conn;
    private TransactionRepository transactionRepository;

    public TransactionService(H2JDBCUtils conn) {
        this.conn = conn;
        this.transactionRepository = new TransactionRepository(conn);
    }

    public void createPaymentTransaction(int idAccountOut, Invoice invoice) {
        Date today = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        transactionRepository.createTransaction(TransactionType.PAYMENT, idAccountOut, 0, invoice.getValue(), today);
    }

    public void createDepositTransaction(int idAccountIn, BigDecimal value) {
        Date today = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        transactionRepository.createTransaction(TransactionType.DEPOSIT, 0, idAccountIn, value, today);
    }

    public void createLoanTransaction(int idAccountIn, BigDecimal value) {
        Date today = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        transactionRepository.createTransaction(TransactionType.LOAN, 0, idAccountIn, value, today);
    }

    public void createTransferTransaction(int idAccountOut, int idAccountIn, BigDecimal value /*,Date dataParcela*/) {
        Date dataParcela = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        transactionRepository.createTransaction(TransactionType.TRANSFER, idAccountOut, idAccountIn, value, dataParcela);
    }
}
