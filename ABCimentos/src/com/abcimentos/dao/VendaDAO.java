package com.abcimentos.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.abcimentos.application.Util;
import com.abcimentos.model.ItemVenda;
import com.abcimentos.model.Usuario;
import com.abcimentos.model.Venda;


public class VendaDAO extends DAO<Venda>  {
	
	@Override
	public boolean create(Venda obj) {
		boolean resultado = false;
		
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return false;
		}
		
		PreparedStatement stat = null;
		try {
			stat =	getConnection().prepareStatement("INSERT INTO venda ( "
										+ "  data, "
										+ "  cliente,"
										+ "  idusuario ) " 
										+ "VALUES ( "
										+ " ?, "
										+ " ?, "
										+ " ? ) ", Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, Date.valueOf(LocalDate.now()));
			stat.setString(2, obj.getCliente());
			stat.setInt(3, obj.getUsuario().getId());
			
			
			stat.executeUpdate();
			final ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			final int lastId = rs.getInt("id");
			
			stat.close();
			
			for (ItemVenda itemVenda : obj.getListaItemVenda()) {
				stat =	getConnection().prepareStatement("INSERT INTO itemvenda ( "
						+ "  valor, "
						+ "  idvenda,"
						+ "  idproduto ) " 
						+ "VALUES ( "
						+ " ?, "
						+ " ?, "
						+ " ? ) ");
				stat.setDouble(1, itemVenda.getValor());
				stat.setInt(2, lastId);
				stat.setInt(3, itemVenda.getProduto().getId());
				
				stat.execute();
				stat.close();
			}

			
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

	public List<Venda> findAll(Usuario usuario) {
		// verificando se tem uma conexao valida
		if (getConnection() == null) {
			Util.addMessageError("Falha ao conectar ao Banco de Dados.");
			return null;
		}
		
		List<Venda> listaVenda = new ArrayList<Venda>();
		
		PreparedStatement stat = null;
	
		try {
			stat = getConnection().prepareStatement("SELECT * FROM venda WHERE idusuario = ?");
			stat.setInt(1, usuario.getId());
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				venda.setData(rs.getDate("data").toLocalDate());
				venda.setCliente(rs.getString("cliente"));
				venda.setUsuario(usuario);
				
				// buscando os itensvenda de cada venda
				ItemVendaDAO dao = new ItemVendaDAO();
				venda.setListaItemVenda(dao.findAll(venda));
				
				// adicionando na lista de retorno
				listaVenda.add(venda);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Util.addMessageError("Falha ao consultar o Banco de Dados. VendaDAO");
			listaVenda = null;
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaVenda;
	}
	
	
	@Override
	public List<Venda> findAll() {
		return null;
	}

	@Override
	public boolean update(Venda obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Venda findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
