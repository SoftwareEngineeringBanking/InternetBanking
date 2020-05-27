package br.com.xurebinhaBanking.model;

import lombok.Data;

@Data
public class Client extends Account {
    private String name;
    private String cpf;
    private String password;
    private String secondPassword;
    private Account account;

    public Client(String name, String cpf, String password, String secondPassword, Account account) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.secondPassword = secondPassword;
        this.account = account;
    }
}
