package br.com.xurebinhaBanking;

import br.com.xurebinhaBanking.model.account.Account;
import br.com.xurebinhaBanking.service.AccountService;
import br.com.xurebinhaBanking.model.account.StatusAccount;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

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

    @Test
    void testMenu() {
        AccountService service = new AccountService();
        String NOVA_LINHA = "\n";

        String menu = "---------------------------------" + NOVA_LINHA +
                "----MENU DE CONTA DO CLIENTE----" + NOVA_LINHA +
                "---------------------------------" + NOVA_LINHA +
                "1 - Fazer Transferencia" + NOVA_LINHA +
                "2 - Fazer Depósito" + NOVA_LINHA +
                "3 - Verificar Saldo" + NOVA_LINHA +
                "4 - Pagar Conta " + NOVA_LINHA +
                "5 - Fazer Emprestimo" + NOVA_LINHA +
                "6 - Pagar Emprestimo" + NOVA_LINHA +
                "7 - Atualizar Dados" + NOVA_LINHA +
                "8 - Alterar senha" + NOVA_LINHA +
                "0 - Retornar ao Menu Inicial";

        assertEquals(service.menu(), menu);
    }

    @Test
    void testMenuAccount() {
        AccountService service = new AccountService();
        String NOVA_LINHA = "\n";

        String menuAccount = "---------------------------------" + NOVA_LINHA +
                "----Atualizar dados----" + NOVA_LINHA +
                "1 - Limite" + NOVA_LINHA +
                "2 - Status" + NOVA_LINHA +
                "0 - Atualizar";

        assertEquals(service.menuAccount(), menuAccount);
    }
}
