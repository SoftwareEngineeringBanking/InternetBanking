package br.com.xurebinhaBanking.login.data.model;

import br.com.xurebinhaBanking.auditable.AuditableEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "login")
public class Login extends AuditableEntity {

    private String cpf;
    private String email;
    private String password;
    private String secondPassword;
}
