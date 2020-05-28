package br.com.xurebinhaBanking.config;

import lombok.Data;

import java.sql.*;

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
    public void dropTable(String sqlDrop){
        try {
            stmt.executeUpdate(sqlDrop);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    public boolean existReg(String sqlExist) {
        ResultSet rs = consultarRegistros(sqlExist);
        boolean exist = false;
        try {
            if (rs.next()){
                exist = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exist;
    }

    public void inserirRegistro(String sqlInsert){
        try {
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    public long insertRegisterAndGetId(String sqlInsert){
        try {
            stmt.executeUpdate(sqlInsert);

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
               return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }

    public void update(String sqlUpdate){
        try {
            stmt.executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void apagarRegistro(String sqlDelete){
        try {
            stmt.executeUpdate(sqlDelete);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public ResultSet consultarRegistros(String sqlSelect){
        ResultSet rs = null;
        try {
             rs = stmt.executeQuery(sqlSelect);
        } catch (SQLException e) {
            printSQLException(e);
        }

        return rs;
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
