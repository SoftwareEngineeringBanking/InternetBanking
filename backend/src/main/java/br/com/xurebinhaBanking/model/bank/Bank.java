package br.com.xurebinhaBanking.model.bank;

import br.com.xurebinhaBanking.model.bank.BankType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
    private int cod;
    private String name;
    private BankType bankType;
    public Bank(int cod){
        this.cod = cod;
        this.name="Xu Banking";
    }
}
