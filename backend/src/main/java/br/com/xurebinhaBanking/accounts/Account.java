package br.com.xurebinhaBanking.accounts;

import java.math.BigDecimal;

public class Account {
    private int agency;
    private int number;
    //private AccountType accountType;
    private double balance;
    //private Bank bank;
    private BigDecimal limitAccount;
    private StatusAccount statusAccount;

    public int getAgency(){
        return agency;
    }
    public void setAgency(int agency) {
        this.agency = agency;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public BigDecimal getLimitAccount(){
        return limitAccount;
    }
    public void setLimitAccount(BigDecimal limitAccount){
        this.limitAccount = limitAccount;
    }
}