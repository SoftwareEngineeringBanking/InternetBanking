package br.com.xurebinhaBanking.model.menu;

public class Menu {
    private static String NOVA_LINHA = "\n";

    public static String menu() {
        return "---------------------------------" + NOVA_LINHA +
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
    }

    public static String menuAccount() {
        return "---------------------------------" + NOVA_LINHA +
                "----Atualizar dados----" + NOVA_LINHA +
                "1 - Limite" + NOVA_LINHA +
                "2 - Número" + NOVA_LINHA +
                "0 - Voltar para o menu anterior!";
    }
}
