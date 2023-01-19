package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.alura.dao.CategoriaDAO;
import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;

public class TestaListagemDeCategorias {

	public static void main(String[] args) throws SQLException {

		try (Connection conexao = new ConnectionFactory().recuperarConexao()) {
			CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
			List<Categoria> listaDeCategorias = categoriaDAO.listarComProdutos();
			listaDeCategorias.stream().forEach(ct -> {
				System.out.println(ct.getNome());
//				try {
//					for(Produto produto : new ProdutoDAO(conexao).buscar(ct)) {
//						System.out.println(ct.getNome() + " - " + produto.getNome());
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//Aqui tentamos fazer um sistema para buscar todas as categorias na tabela e então buscar cada produto que está relacionado a aquela categoria, porém esse jeito não é uma boa prática por que aqui nós fazemos algo que é chamado de "Queries N + 1" que significa que nós chamamos a categoriaDAO para iterar sobre as categorias e para cada categoria nós usamos o ProdutoDAO para iterar sobre os produtos dentro daquela categoria, nesse caso isso exige muita performance pode ser custoso caso o banco de dados seja grande, fazendo com que se torne inviável o número de buscas no banco de dados

				// O jeito corrigido a seguir:
				for (Produto produto : ct.getProdutos()) {
					System.out.println(ct.getNome() + " - " + produto.getNome());
				}
			});
		}

	}

}
