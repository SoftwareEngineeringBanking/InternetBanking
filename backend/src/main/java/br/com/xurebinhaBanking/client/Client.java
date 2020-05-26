package br.com.xurebinhaBanking.client;

import br.com.xurebinhaBanking.account.Account;

public class Client extends Account {
    private String name;
    private String cpf;
    private String password;
    private String secondPassword;
    private Account account;

    public Client() {
        account = new Account();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondPassword() {
        return secondPassword;
    }
    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }
}
