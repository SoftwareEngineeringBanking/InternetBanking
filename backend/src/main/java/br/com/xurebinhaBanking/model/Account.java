package br.com.xurebinhaBanking.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private int agency;
    private int number;
    private BigDecimal balance;
    private BigDecimal limitAccount;
    private StatusAccount statusAccount;
    private AccountType accountType;
    private Bank bank;

    public Account(int agency, int number, BigDecimal balance, BigDecimal limitAccount,
                   StatusAccount statusAccount, AccountType accountType, Bank bank) {
        this.agency = agency;
        this.number = number;
        this.balance = balance;
        this.limitAccount = limitAccount;
        this.statusAccount = statusAccount;
        this.accountType = accountType;
        this.bank = bank;
    }

}