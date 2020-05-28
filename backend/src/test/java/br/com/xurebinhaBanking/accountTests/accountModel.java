package br.com.xurebinhaBanking.accountTests;

import br.com.xurebinhaBanking.model.Account;
import br.com.xurebinhaBanking.model.StatusAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class accountModel {

    @Test
    void testAccountModel() {
        Account account = new Account();
        account.setAgency(5582);
        account.setNumber(806789);
        account.setStatusAccount(StatusAccount.ACTIVE);

        assertNotNull(account);
        assertEquals(5582, account.getAgency());
        assertEquals(806789, account.getNumber());
        assertEquals("ACTIVE", account.getStatusAccount());
    }

}
