package br.com.xurebinhaBanking.account.data.model;

import lombok.Data;

@Data

public class Account{

    private String id;
    private Client client;
    private String number;
    private String agency;
    private Boolean status;
    private AccountType accountType;
    private double limit;
    private double balance;

}
