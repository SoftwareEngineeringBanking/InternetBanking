package br.com.xurebinhaBanking;

import br.com.xurebinhaBanking.model.client.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    void testClientModel() {
        Client client =  new Client();
        client.setName("Jackie Chan");
        client.setCpf("733.665.800-89");
        client.setPassword("admin");
        client.setSecondPassword("admin2");

        assertNotNull(client);
        assertEquals("Jackie Chan", client.getName());
        assertEquals("733.665.800-89", client.getCpf());
        assertEquals("admin", client.getPassword());
        assertEquals("admin2", client.getSecondPassword());
    }
}
