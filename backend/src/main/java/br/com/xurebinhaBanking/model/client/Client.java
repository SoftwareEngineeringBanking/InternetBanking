package br.com.xurebinhaBanking.model.client;

import br.com.xurebinhaBanking.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client{
    private int id;
    private String name;
    private String cpf;
    private String password;
    private String secondPassword;
    private List<Account> accountList;

    public Client(String name,String cpf, String password, String secondPassword){
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.secondPassword = secondPassword;
        this.accountList = new ArrayList<>();
    }
}
