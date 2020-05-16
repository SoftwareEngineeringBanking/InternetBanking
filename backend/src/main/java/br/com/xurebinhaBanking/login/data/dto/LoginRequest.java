package br.com.xurebinhaBanking.login.data.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotBlank
    @Pattern(regexp = "[0-9]+", message = "just enter numbers")
    @Size(min = 11, max = 11, message = "cpf must be 11 digits")
    private String cpf;
    @Email
    private String email;
    @NotBlank
    private String password;
    private String secondPassword;
}
