package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestaListagem {
	
	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory(); //Aqui criamos um novo objeto da classe "CriaConexao" para podermos usar seus métodos
		Connection conexao = connectionFactory.recuperarConexao(); //Aqui a referencia "conexao" do tipo "Connection" vai receber o retorno do método "recuperarConexao" da nossa classe
		
//		Statement stm = conexao.createStatement(); //Usando o método "createStatement" para poder usar os comandos do SQL no Java, já que para o Java os comandos do SQL são vistos como "Statements"
//		boolean resultado = stm.execute("SELECT * FROM Produto"); //Ao tentar executar o método "execute" de um "Statement" e passarmos o comando que queremos fazer como parametro nos será retornado um boolean
		
		
		//Aplicando a nova forma de executar um statement ao invés do jeiot antigo que continha riscos, desse jeito nós podemos nos preparar para caso precisemos receber algum atributo para aplicar no statement, tipo um indice para um "where"
		PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Produto");
		stm.execute();
		
//		System.out.println(resultado); //Caso o comando retorne uma lista (como é o caso do "SELECT" que serve para exibir a tabela) o boolean será true, mas caso for algum comando que não retorne nada (como um INSERT, DELETE, UPDATE...) o boolean será false
		
		ResultSet rst = stm.getResultSet(); //Para realmente termos o retorno da tabela SQL é necessário usar o método "getResultSet" da classe "ResultSet"
		
		while(rst.next()) { //Aqui nós fazemos um laço para verificar se existe um próximo item na tabela, caso exista ele será pego pelo while, se ele não existir o laço será encerrado, para fazer isso nos delegamos para o método "next"
			Integer id = rst.getInt("ID"); //Aqui nós guardaremos as informações recebidas pela tabela em seus tipos esperados (números dentro de INT e palavras dentro de Strings)
			System.out.println(id); //E em seguida imprimimos o resultado na tela, repetimos esse processo para cada campo
			String nome = rst.getString("NOME");
			System.out.println(nome);
			String descricao = rst.getString("DESCRICAO"); //Os métodos "getAlgumTipo" podem receber dois parametros para buscarem um campo, o "label" que é a busca pelo nome do campo como fizemos aqui, ou então a busca pelo index, no caso do index seria "se eu quero o primeiro campo eu busco o 1, se eu quero o segundo eu busco pelo 2..." aop contrário do Java o indice no SQL não começa em 0
			System.out.println(descricao);
		}
		
		conexao.close();
		
	}

}
