package br.com.xurebinhaBanking.bank;

public class Bank {
    private int cod;
    private String name;
    //private BankType bankType;

    public int getCod(){
        return cod;
    }
    public void setCod(int cod){
        this.cod = cod;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
