package Pratica7;

import javax.swing.*;
import java.sql.*;

public class Conexao {
    private final String url = "jdbc:postgresql://localhost/BDlivrariaUniversitaria"; //o postgreql vai funcionar devido a dependencia da biblioteca
    private final String user = "postgres";
    private final String password = "123456";

    Connection conn = null; //Varivael de conexão
    Conexao conexao;


    //Variaveis que não vão mudar que são da classe
    //São necessarias para fazer select, delete e insert

    private static final String QUERY_LIVRO_BY_TITLE = "SELECT * FROM livro WHERE nm_titulo LIKE ?";
    private static final String QUERY_LIVRO_BY_PRICE = "SELECT * FROM livro WHERE vl_preco >= ?";


    public Connection connect() {
        //Ele vai ter que devolver conexão do banco de dados, para funcionar ele vai precisar try and catch

        try {
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server successfully."); //deu certo
            } else {
                System.out.println("Failed to make connection!");//falha
            }
            //versão do postgreeSQL
            Statement statement = conn.createStatement(); //ponte entre postgre e java, para conseguir executar algum comando
            //ResultSet é uma classe e resultSet é uma classe que da de retorno um resultado
            ResultSet resultSet = statement.executeQuery("SELECT VERSION()"); //todos os comandos precisam estar entre aspas
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));//Buscar resultado no formato de string
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn; //E o que retorna Connection
        //conn.close();
    }
    public void buscaLivroPorTitulo() { //retona todos os usuarios

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_LIVRO_BY_TITLE);
            preparedStatement.setString(1, JOptionPane.showInputDialog("Digite o titulo:") + "%");//1 é ordem do paramaetro, e 5 o valor que no caso seria o id_autor
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String nm_titulo = rs.getString("nm_titulo");
                System.out.println(nm_titulo);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    public void buscaLivroPorPreco() { //retona todos os usuarios

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_LIVRO_BY_PRICE);
            preparedStatement.setDouble(1, Double.parseDouble(JOptionPane.showInputDialog("Digite o valor do livro: ")));//1 é ordem do paramaetro, e 5 o valor que no caso seria o id_autor
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String nm_titulo = rs.getString("nm_titulo");
                double vl_preco = rs.getDouble("vl_preco");
                System.out.println(nm_titulo + " - " + vl_preco);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
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
