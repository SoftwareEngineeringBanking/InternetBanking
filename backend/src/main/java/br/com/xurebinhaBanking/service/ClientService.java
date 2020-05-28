package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.dao.AccountRepository;
import br.com.xurebinhaBanking.dao.ClientRepository;
import br.com.xurebinhaBanking.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class ClientService {
    private static H2JDBCUtils conn;
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
    private final static int AGENCY_DEFAULT = 0001;

    public ClientService(H2JDBCUtils conn) {
        this.conn = conn;
        this.accountRepository = new AccountRepository(conn);
        this.clientRepository = new ClientRepository(conn);
    }

    public static void createClientAccount(){
        Scanner in = new Scanner(System.in);

        System.out.println("------------ MENU DE CRIACAO DE CONTA ------------");
        System.out.println("Digite seu nome:");
        String name = in.next();

        System.out.println("Digite seu CPF:");
        String cpf = in.next();

        System.out.println("Digite sua senha:");
        String password = in.next();

        System.out.println("Digite sua segunda senha:");
        String secondPassword = in.next();

        System.out.println("Selecione a agencia: \n"+listAgency());
        int agency = in.nextInt();
        //todo validar escolha agencia


        Client client = new Client(name, cpf, password, secondPassword);
        clientRepository.createClient(client);

        Account account = new Account(client.getId(), agency, generateNumAccount(), getDefaultBalance(), getLimitAccount(), getDefaultStatusAccount(), getDefaultAccountType(), getDefaultBank());
        client.getAccountList().add(account);
        accountRepository.createAccount(account);

    }

    //todo listar e bucas dados de banco
    private static Bank getDefaultBank() {
        return new Bank(0,"Xurebinha",BankType.INTERNAL);
    }

    //TODO mostrar lista de agencias
    private static String listAgency(){
        return "Agencia principal - "+AGENCY_DEFAULT;
    }
    //todo criar lista de bancos
    //todo criar forma de gerar numero da conta
    private static int generateNumAccount(){
        return 0001;
    }

    //todo validar o balance
    private static BigDecimal getDefaultBalance(){
        return new BigDecimal(0);
    }
    //todo buscar limite da conta com o tipo da conta
    private static BigDecimal getLimitAccount(){
        return new BigDecimal(0);
    }

    private static StatusAccount getDefaultStatusAccount(){
        return StatusAccount.ACTIVE;
    }

    //Todo validar tipo da conta e buscar o tipo da conta
    private static AccountType getDefaultAccountType(){
        return new AccountType("PADRAO",0,0,0,0,0);
    }

}
