package br.com.xurebinhaBanking.account.data.dto;

import lombok.Data;

@Data
public class AccountRequest {

    //@NotBlank
    //@Pattern(regexp = "[0-9]+", message = "just enter numbers")
    //@Size(min = 6, max = 6, message = "number must be 6 digits")
    private String number;

    //@NotBlank
    //@Pattern(regexp = "[0-9]+", message = "just enter numbers")
    //@Size(min = 4, max = 4, message = "agency must be 4 digits")
    private String agency;

    //@NotBlank
    //@Pattern(regexp = "[0-9]+", message = "just enter numbers")
    //@Size(min = 11, max = 11, message = "cpf must be 11 digits")
    private String cpf;
    private Boolean status;
}
