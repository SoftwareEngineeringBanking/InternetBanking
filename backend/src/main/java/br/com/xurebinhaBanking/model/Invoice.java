package br.com.xurebinhaBanking.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Invoice {
    private int id;
    private String codeBar;
    private Date datePayment;
    private Date   dateEnd;
    private BigDecimal value;

    public Invoice(String codeBar){
        //11111DDMMAAAA3333333333333
        //11111 = identificador
        //DDMMAAAA = data vencimento
        //3333333333333 = valor 333.333.333.333,33

        this.codeBar = codeBar;
        this.id = Integer.decode(codeBar.substring(0,5));
        //this.dateEnd = "";
        this.value = new BigDecimal(codeBar.substring(12,codeBar.length()));

    }
}
