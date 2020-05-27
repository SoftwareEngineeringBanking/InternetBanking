package br.com.xurebinhaBanking.bank;

import lombok.Data;

@Data
public class Bank {
    private int cod;
    private String name;
    private BankType bankType;
}
