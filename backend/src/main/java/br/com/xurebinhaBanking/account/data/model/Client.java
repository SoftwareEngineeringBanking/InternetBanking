package br.com.xurebinhaBanking.account.data.model;

import lombok.Data;

@Data
public class Client {
    private int id;
    private String cpf;
    private Boolean status;
    private String password;
    private String secondPassword;
}
