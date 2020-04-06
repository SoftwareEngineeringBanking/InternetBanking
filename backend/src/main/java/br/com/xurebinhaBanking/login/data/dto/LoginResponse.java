package br.com.xurebinhaBanking.login.data.dto;

import br.com.xurebinhaBanking.auditable.AuditableEntity;
import lombok.Data;

@Data
public class LoginResponse extends AuditableEntity {

    private String cpf;
    private String email;
}
