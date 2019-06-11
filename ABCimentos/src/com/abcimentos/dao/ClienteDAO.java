package com.abcimentos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.abcimentos.application.Util;
import com.abcimentos.model.Cliente;
import com.abcimentos.model.Estado;

public class ClienteDAO extends DAO<Cliente>{

	@Override
	public boolean create(Cliente obj) {
		boolean resultado = false;
		
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}
		
		PreparedStatement stat = null;
		try {
			stat =	getConnection().prepareStatement("INSERT INTO cliente ( "
										+ " nome, "
										+ " cpf, "
										+ " cnpj, "
										+ " endereco, "
										+ " cidade, "
										+ " estado, "
										+ " email )"
										+ "VALUES ( "
										+ " ?, "
										+ " ?, "
										+ " ?, "
										+ " ?, "
										+ " ?, "
										+ " ?,"
										+ " ? ) ");
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			stat.setString(3, obj.getCnpj());
			stat.setString(4, obj.getEndereco());
			stat.setString(5, obj.getCidade());
			stat.setInt(6, obj.getEstado().getValue());	
			stat.setString(7, obj.getEmail());
			
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
	public boolean update(Cliente obj) {
		boolean resultado = false;
		
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}
		
		PreparedStatement stat = null;
		try {
			stat =	getConnection().prepareStatement("UPDATE cliente SET "
												   + "  nome = ?, "
												   + "  cpf = ?, "
												   + "  cnpj = ?, "
												   + "  endereco = ?, "
												   + "  cidade = ?, "
												   + "  estado = ?, "
												   + "  email = ?  "
												   + "WHERE id = ? ");
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			stat.setString(3, obj.getCnpj());
			stat.setString(4, obj.getEndereco());
			stat.setString(5, obj.getCidade());
			stat.setInt(6, obj.getEstado().getValue());	
			stat.setString(7, obj.getEmail());
			
			stat.setInt(8, obj.getId());
			
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
			stat =	getConnection().prepareStatement("DELETE FROM cliente WHERE id = ? ");
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
	public Cliente findById(int id) {
		// verificando se tem uma conexao valida
				if (getConnection() == null) {
					Util.addMessageError("Falha ao conectar ao Banco de Dados.");
					return null;
				}
				Cliente cliente = null;
				
				PreparedStatement stat = null;
				
				try {
					stat = getConnection().prepareStatement("SELECT * FROM cliente WHERE id = ?");
					stat.setInt(1, id);
					
					ResultSet rs = stat.executeQuery();
					if(rs.next()) {
						cliente = new Cliente();
						cliente.setId(rs.getInt("id"));
						cliente.setNome(rs.getString("nome"));
						cliente.setCpf(rs.getString("cpf"));
						cliente.setCnpj(rs.getString("cnpj"));
						cliente.setEndereco(rs.getString("cidade"));
						cliente.setCidade(rs.getString("cidade"));
						cliente.setEstado(Estado.valueOf(rs.getInt("estado")));
						cliente.setEmail(rs.getString("email"));
						
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
				return cliente;
	}

	@Override
	public List<Cliente> findAll() {
		// verificando se tem uma conexao valida
				if (getConnection() == null) {
					Util.addMessageError("Falha ao conectar ao Banco de Dados.");
					return null;
				}
				
				List<Cliente> listaCliente = new ArrayList<Cliente>();
				
				PreparedStatement stat = null;
			
				try {
					stat = getConnection().prepareStatement("SELECT * FROM cliente");
					ResultSet rs = stat.executeQuery();
					while(rs.next()) {
						Cliente cliente = new Cliente();
						cliente.setId(rs.getInt("id"));
						cliente.setNome(rs.getString("nome"));
						cliente.setCpf(rs.getString("cpf"));
						cliente.setCnpj(rs.getString("cnpj"));
						cliente.setEndereco(rs.getString("cidade"));
						cliente.setCidade(rs.getString("cidade"));
						cliente.setEstado(Estado.valueOf(rs.getInt("estado")));
						cliente.setEmail(rs.getString("email"));
						
						listaCliente.add(cliente);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					Util.addMessageError("Falha ao consultar o Banco de Dados.");
					listaCliente = null;
				} finally {
					try {
						stat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return listaCliente;
	}
	
	public List<Cliente> findByNome(String nome) {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}
		
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		
		PreparedStatement stat = null;
	
		try {
			stat = getConnection().prepareStatement("SELECT * FROM cliente WHERE nome ILIKE ?");
			stat.setString(1, (nome == null? "%" : "%"+nome+"%"));
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setCnpj(rs.getString("cnpj"));
				cliente.setEndereco(rs.getString("cidade"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEstado(Estado.valueOf(rs.getInt("estado")));
				cliente.setEmail(rs.getString("email"));
				
				listaCliente.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados.");
			listaCliente = null;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaCliente;
	}

}
