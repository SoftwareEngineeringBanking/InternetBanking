package br.com.xurebinhaBanking.model.invoice;

import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        //00001190620200000000000010
        //11111 = identificador
        //DDMMAAAA = data vencimento
        //3333333333333 = valor 333.333.333.333,33

        this.codeBar = codeBar;
        this.id = Integer.decode(codeBar.substring(0,5));
        String date = codeBar.substring(5,13);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        try {
            this.dateEnd = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.value = new BigDecimal(codeBar.substring(13,codeBar.length()));

    }
}
