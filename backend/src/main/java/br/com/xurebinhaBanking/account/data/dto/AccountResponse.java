package br.com.xurebinhaBanking.account.data.dto;


import lombok.Data;

@Data
public class AccountResponse {

    private String number;
    private String agency;
    private String cpf;
    private Boolean status;
}
