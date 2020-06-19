package br.com.xurebinhaBanking.model.billet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Billet {

    private String codeBar;
    private String date;
}
