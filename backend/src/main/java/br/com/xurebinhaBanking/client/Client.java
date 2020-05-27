package br.com.xurebinhaBanking.client;

import br.com.xurebinhaBanking.account.Account;
import lombok.Data;

@Data
public class Client extends Account {
    private String name;
    private String cpf;
    private String password;
    private String secondPassword;
    private Account account;
    public Client() {
        account = new Account();
    }
}
