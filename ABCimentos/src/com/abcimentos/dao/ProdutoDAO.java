package com.abcimentos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.abcimentos.application.Util;
import com.abcimentos.model.Produto;

public class ProdutoDAO extends DAO<Produto> {

	@Override
	public boolean create(Produto obj) {
		boolean resultado = false;
		
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}
		
		PreparedStatement stat = null;
		try {
			stat =	getConnection().prepareStatement("INSERT INTO produto ( "
										+ " nome, "
										+ " descricao, "
										+ " quantidade, "
										+ " peso, "
										+ " unidade, "
										+ " valor )"
										+ "VALUES ( "
										+ " ?, "
										+ " ?, "
										+ " ?, "
										+ " ?, "
										+ " ?,"
										+ " ? ) ");
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setInt(3, obj.getQuantidade());	
			stat.setString(4, obj.getPeso());
			stat.setString(5, obj.getUnidade());
			stat.setDouble(6,  obj.getValor());
			
			stat.execute();
			Util.addMessageError("Cadastro realizado com sucesso!");
			resultado = true;
		} catch (SQLException e) {
			Util.addMessageError("Falha ao incluir.");
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	@Override
	public boolean update(Produto obj) {
		boolean resultado = false;
		
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}
		
		PreparedStatement stat = null;
		try {
			stat =	getConnection().prepareStatement("UPDATE produto SET "
												   + "  nome = ?, "
												   + "  descricao = ?, "
												   + "  quantidade = ?, "
												   + "  peso = ?,  "
												   + "  unidade = ?,  "
												   + "  valor = ?  "
												   + "WHERE id = ? ");
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setInt(3, obj.getQuantidade());	
			stat.setString(4, obj.getPeso());
			stat.setString(5, obj.getUnidade());
			stat.setDouble(6, obj.getValor());
			
			stat.setInt(7, obj.getId());
			
			stat.execute();
			Util.addMessageError("Alteração realizada com sucesso!");
			resultado = true;
		} catch (SQLException e) {
			Util.addMessageError("Falha ao Alterar.");
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	@Override
	public boolean delete(int id) {
		boolean resultado = false;
		
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}
		
		PreparedStatement stat = null;
		try {
			stat =	getConnection().prepareStatement("DELETE FROM produto WHERE id = ? ");
			stat.setInt(1, id);
			
			stat.execute();
			Util.addMessageError("Exclusão realizada com sucesso!");
			resultado = true;
		} catch (SQLException e) {
			Util.addMessageError("Falha ao Excluir.");
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	@Override
	public Produto findById(int id) {
		// verificando se tem uma conexao valida
				if (getConnection() == null) {
					Util.addMessageError("Falha ao conectar ao Banco de Dados.");
					return null;
				}
				Produto produto = null;
				
				PreparedStatement stat = null;
				
				try {
					stat = getConnection().prepareStatement("SELECT * FROM produto WHERE id = ?");
					stat.setInt(1, id);
					
					ResultSet rs = stat.executeQuery();
					if(rs.next()) {
						produto = new Produto();
						produto.setId(rs.getInt("id"));
						produto.setNome(rs.getString("nome"));
						produto.setDescricao(rs.getString("descricao"));
						produto.setQuantidade(rs.getInt("quantidade"));	
						produto.setPeso(rs.getString("peso"));
						produto.setUnidade(rs.getString("unidade"));
						produto.setValor(rs.getDouble("valor"));
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
					Util.addMessageError("Falha ao consultar o Banco de Dados.");
				} finally {
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return produto;
	}

	@Override
	public List<Produto> findAll() {
		// verificando se tem uma conexao valida
				if (getConnection() == null) {
					Util.addMessageError("Falha ao conectar ao Banco de Dados.");
					return null;
				}
				
				List<Produto> listaProduto = new ArrayList<Produto>();
				
				PreparedStatement stat = null;
			
				try {
					stat = getConnection().prepareStatement("SELECT * FROM produto");
					ResultSet rs = stat.executeQuery();
					while(rs.next()) {
						Produto produto = new Produto();
						produto.setId(rs.getInt("id"));
						produto.setNome(rs.getString("nome"));
						produto.setDescricao(rs.getString("descricao"));
						produto.setQuantidade(rs.getInt("quantidade"));	
						produto.setPeso(rs.getString("peso"));
						produto.setUnidade(rs.getString("unidade"));
						produto.setValor(rs.getDouble("valor"));
						

						listaProduto.add(produto);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					Util.addMessageError("Falha ao consultar o Banco de Dados.");
					listaProduto = null;
				} finally {
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return listaProduto;
	}
	
	public List<Produto> findByNome(String nome) {
		// verificando se tem uma conexao valida
				if (getConnection() == null) {
					Util.addMessageError("Falha ao conectar ao Banco de Dados.");
					return null;
				}
				
				List<Produto> ListaProduto = new ArrayList<Produto>();
				
				PreparedStatement stat = null;
			
				try {
					stat = getConnection().prepareStatement("SELECT * FROM Produto WHERE nome ILIKE ?");
					stat.setString(1, (nome == null? "%" : "%"+nome+"%"));
					ResultSet rs = stat.executeQuery();
					
					while(rs.next()) {
						Produto produto = new Produto();
						produto.setId(rs.getInt("id"));
						produto.setNome(rs.getString("nome"));
						produto.setDescricao(rs.getString("descricao"));
						produto.setQuantidade(rs.getInt("quantidade"));	
						produto.setPeso(rs.getString("peso"));
						produto.setUnidade(rs.getString("unidade"));
						produto.setValor(rs.getDouble("valor"));
						

						ListaProduto.add(produto);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					Util.addMessageError("Falha ao consultar o Banco de Dados.");
					ListaProduto = null;
				} finally {
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return ListaProduto;
	}
}
