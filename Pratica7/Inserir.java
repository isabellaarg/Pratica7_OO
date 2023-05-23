package Pratica7;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Inserir {
    private final String url = "jdbc:postgresql://localhost/BDlivrariaUniversitaria";
    private final String user = "postgres";
    private final String password = "123456";

    private String id_isbn,nm_titulo;
    private double vl_preco;

    private static final String INSERT_LIVRO_SQL = "INSERT INTO livro" +
            "  (id_isbn, nm_titulo, vl_preco) VALUES " +
            " (?, ?, ?);";

    public static void main(String[] argv) throws SQLException {
        Inserir createTableExample = new Inserir();
        createTableExample.insertRecord();
    }

    public void insertRecord() throws SQLException {
        System.out.println(INSERT_LIVRO_SQL);
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LIVRO_SQL)) {

            preparedStatement.setString(1, id_isbn = JOptionPane.showInputDialog("Digite o id_isbn"));
            preparedStatement.setString(2, nm_titulo = JOptionPane.showInputDialog("Digite o nm_titulo"));
            preparedStatement.setDouble(3, vl_preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o nm_titulo")));

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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
