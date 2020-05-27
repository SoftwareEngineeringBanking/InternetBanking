package br.com.xurebinhaBanking.config;

public class CreateTablesAndDefaultRegisters {

    private static final String tblBase = "CREATE TABLE if not exists ";

    private static final String tblClient = "client(ID INT PRIMARY KEY AUTO_INCREMENT, name varchar(100), cpf varchar(15), password varchar(255), second_password varchar(255))";

    private static final String tblAccount = "account(ID INT PRIMARY KEY AUTO_INCREMENT," +
            " agency int," +
            " number_account int," +
            " type_account varchar(50)," +
            " balance decimal," +
            " bank varchar(50)," +
            " limit_account decimal," +
            " status_account varchar(50))";

    private static final String tblAccountType= "account_type(ID INT PRIMARY KEY AUTO_INCREMENT," +
            " type_name_account varchar(150)," +
            " number_of_transfer_free int," +
            " days_count_for_transfer int," +
            " value_per_transfer decimal," +
            " limit_weekend_days int," +
            " limit_normal_days int)";

    private static final String tblTransaction = "transaction(ID INT PRIMARY KEY AUTO_INCREMENT," +
            " type_transaction int," +
            " number_account int," +
            " account_out int," +
            " account_in int," +
            " value_transfer decimal," +
            " date date," +
            " due_date timestamp)";

    private static final String tblTransactionType = "transaction_type(ID INT PRIMARY KEY AUTO_INCREMENT, name varchar(100))";
    private static final String insTblTransactionType = "('TRANSFER'), ('PAYMENT'), ('DEPOSIT'), ('LOAN'), ('TAX_ACCOUNT'), ('LOAN_PAYMENT')";

    private static final String tblAccountStatus = "account_status(ID INT PRIMARY KEY AUTO_INCREMENT, name varchar(100))";
    private static final String insTblAccountStatus = "('ACTIVE'), ('INACTIVE'), ('BLOCKED'), ('SUSPEND'), ('IN_DEBT')";

    private static final String tblBank = "bank(ID INT PRIMARY KEY AUTO_INCREMENT, cod int, name varchar(150), bank_type int)";

    private static final String tblBankType = "bank_type(ID INT PRIMARY KEY AUTO_INCREMENT, name varchar(150))";
    private static final String insTblBankType = "('INTERNAL'), ('EXTERNAL')";

    public CreateTablesAndDefaultRegisters(H2JDBCUtils conn){
        createTables(conn);
        deleteOldRegisters(conn);
        insertRegisters(conn);
    }

    private void insertRegisters(H2JDBCUtils conn) {
        //insere registros padroes
        conn.inserirRegistro("INSERT INTO transaction_type (name) VALUES " + insTblTransactionType);
        conn.inserirRegistro("INSERT INTO account_status (name) VALUES "+ insTblAccountStatus );
        conn.inserirRegistro("INSERT INTO bank_type (name) VALUES "+ insTblBankType );
    }

    private void createTables(H2JDBCUtils conn) {
        conn.criarTabela(tblBase + tblClient);
        conn.criarTabela(tblBase + tblAccount);
        conn.criarTabela(tblBase + tblAccountType);
        conn.criarTabela(tblBase + tblTransaction);
        conn.criarTabela(tblBase + tblTransactionType);
        conn.criarTabela(tblBase + tblAccountStatus);
        conn.criarTabela(tblBase + tblBank);
        conn.criarTabela(tblBase + tblBankType);
    }

    private void deleteOldRegisters(H2JDBCUtils conn){
        conn.apagarRegistro("DELETE FROM transaction_type");
        conn.apagarRegistro("DELETE FROM account_status");
        conn.apagarRegistro("DELETE FROM bank_type");
    }
}
