package br.com.xurebinhaBanking.service;

import br.com.xurebinhaBanking.config.H2JDBCUtils;
import br.com.xurebinhaBanking.dao.AccountRepository;
import br.com.xurebinhaBanking.dao.ClientRepository;
import br.com.xurebinhaBanking.model.Account;
import br.com.xurebinhaBanking.model.Client;

import java.util.Scanner;

public class ClientService {
    private static H2JDBCUtils conn;
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
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

        /*
        int agency, int number, BigDecimal balance, BigDecimal limitAccount,
                   StatusAccount statusAccount, AccountType accountType, Bank bank
         */
        Account account = new Account();
        Client client = new Client(name, cpf, password, secondPassword, account);

        accountRepository.createAccount(account);
        clientRepository.createClient(client);
    }
}
