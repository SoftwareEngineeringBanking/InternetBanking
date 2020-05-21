package br.com.xurebinhaBanking.config;

import java.sql.Connection;

public class CreateTables {

    private static final String tblBase = "CREATE TABLE if not exists ";

    private static final String tblConta = "account(ID INT PRIMARY KEY AUTO_INCREMENT, login varchar(255), senha varchar(255), nome varchar(255), cpf varchar(255))";

    public CreateTables(H2JDBCUtils conn){
        conn.criarTabela(tblBase + tblConta);
        //CREATE TABLE cars(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), price INT);
    }
}
