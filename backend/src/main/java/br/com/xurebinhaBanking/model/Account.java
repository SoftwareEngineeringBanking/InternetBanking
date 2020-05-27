package br.com.xurebinhaBanking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int agency;
    private int number;
    private BigDecimal balance;
    private BigDecimal limitAccount;
    private StatusAccount statusAccount;
    private AccountType accountType;
    private Bank bank;
}