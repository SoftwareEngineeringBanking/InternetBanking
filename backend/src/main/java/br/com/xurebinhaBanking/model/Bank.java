package br.com.xurebinhaBanking.model;

import lombok.Data;

@Data
public class Bank {
    private int cod;
    private String name;
    private BankType bankType;
}
