package br.com.xurebinhaBanking.accountTests;

import br.com.xurebinhaBanking.accounts.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    void testAccountLogin() {
        Account account = new Account();
        account.setLogin("adminLogin");
        account.setPassword("adminSenha");

        assertNotNull(account.getLogin());
        assertNotNull(account.getPassword());
        assertEquals("adminLogin", account.getLogin());
        assertEquals("adminSenha", account.getPassword());
    }

    @Test
    void testAccountUser(){
        Account account = new Account();
        account.setName("Bruce Wayne");
        account.setCpf("689.376.920-23");

        assertNotNull(account.getName());
        assertNotNull(account.getCpf());
        assertEquals("Bruce Wayne", account.getName());
        assertEquals("689.376.920-23", account.getCpf());
    }

    @Test
    void testAccountBalance(){
        Account account = new Account();
        account.setBalance(10.000);

        assertNotNull(account.getBalance());
        assertTrue(account.getBalance() > 5.000);
        assertFalse(account.getBalance() > 50.000);
    }
}
