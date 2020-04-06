package br.com.xurebinhaBanking.account.data.dto;

import br.com.xurebinhaBanking.auditable.AuditableEntity;
import lombok.Data;

@Data
public class AccountResponse extends AuditableEntity {

    private String number;
    private String agency;
    private String cpf;
    private Boolean status;
}
