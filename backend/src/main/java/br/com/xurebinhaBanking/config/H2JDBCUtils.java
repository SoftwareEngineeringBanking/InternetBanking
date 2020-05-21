package br.com.xurebinhaBanking.config;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Data
public class H2JDBCUtils {
    private static String jdbcURL = "jdbc:h2:~/xurebinhabanking";
    private static String jdbcUsername = "admin";
    private static String jdbcPassword = "admin";
    private static Connection conn = null;
    private static Statement stmt = null;

    public H2JDBCUtils() {
        conn = getConnection();
        stmt = getStatement();
    }

    public void criarTabela(String sqlCreate){
        try {
            stmt.executeUpdate(sqlCreate);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void inserirRegistro(String sqlInsert){
        try {
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void fecharConexao() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return conn;
    }

    private static Statement getStatement() {
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return stmt;
    }

    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
