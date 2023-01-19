package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.alura.dao.ProdutoDAO;
import br.com.alura.jdbc.modelo.Produto;

public class TestaInsercaoComDAO {

	public static void main(String[] args) throws SQLException {

		Produto comoda = new Produto("Cômoda", "Cômoda Vertical");

		try (Connection conexao = new ConnectionFactory().recuperarConexao()) {
			
			ProdutoDAO produtoDAO = new ProdutoDAO(conexao); //Aqui criamos o nosso DAO de produto e passamos a nossa conexão como parametro
			produtoDAO.salvar(comoda); //Já no método salvar nós passamos o produto específico que queremos salvar
			
			List<Produto> listaProdutos = produtoDAO.listar();
			listaProdutos.stream().forEach(lp -> System.out.println(lp));

		}

	}

}
