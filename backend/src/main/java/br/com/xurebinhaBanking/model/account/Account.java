package br.com.xurebinhaBanking.model.account;

import br.com.xurebinhaBanking.model.bank.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int id;
    private int clientId;
    private int agency;
    private int number;
    private BigDecimal balance;
    private BigDecimal limitAccount;
    private StatusAccount statusAccount;
    private AccountType accountType;
    private Bank bank;

    public Account(int clientId, int agency, int number, BigDecimal balance, BigDecimal limitAccount,
                   StatusAccount statusAccount, AccountType accountType, Bank bank){
        this.clientId = clientId;
        this.agency = agency;
        this.number = number;
        this.balance = balance;
        this.limitAccount = limitAccount;
        this.statusAccount = statusAccount;
        this.accountType = accountType;
        this.bank = bank;
    }
}