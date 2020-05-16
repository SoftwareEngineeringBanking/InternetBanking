package br.com.xurebinhaBanking.account.data.model;

import br.com.xurebinhaBanking.auditable.AuditableEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "account")
public class Account extends AuditableEntity {

    private String id;
    private Client client;
    private String number;
    private String agency;
    private Boolean status;
    private AccountType accountType;
    private double limit;
    private double balance;

}
