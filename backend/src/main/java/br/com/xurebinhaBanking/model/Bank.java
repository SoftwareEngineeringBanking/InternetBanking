package br.com.xurebinhaBanking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bank {
    private int cod;
    private String name;
    private BankType bankType;
}
