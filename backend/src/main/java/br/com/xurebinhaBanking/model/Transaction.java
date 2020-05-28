package br.com.xurebinhaBanking.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Transaction {
    private int id;
    private TransactionType transactionType;
    private Account accountOut;
    private Account accountIn;
    private BigDecimal valueToTransfer;
    private Date date;
    private Date dueDate;
}
