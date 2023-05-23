package Pratica7;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Conexao conexao = new Conexao();
        Inserir inserir = new Inserir();
        Excluir excluir = new Excluir();
        conexao.connect();
        int opcao = Integer.parseInt(JOptionPane.showInputDialog("<1> Cadastrar Livro \n<2> Pesquisar Livro por Preço \n<3> Pesquisar Livro por Título \n<4> Excluir Livro \n<5> Sair"));

        while(opcao != 5) {

            switch (opcao) {
                case 1:
                    inserir.insertRecord();
                    break;

                case 2:
                    conexao.buscaLivroPorPreco();
                    break;

                case 3:
                    conexao.buscaLivroPorTitulo();
                    break;

                case 4:
                    excluir.deleteRecord();
                    break;

                default:
                    break;
            }
        }
    }
}
