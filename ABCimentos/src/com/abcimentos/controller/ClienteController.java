package com.abcimentos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.abcimentos.dao.ClienteDAO;
import com.abcimentos.model.Cliente;
import com.abcimentos.model.Estado;


public class ClienteController  implements Serializable {

	private static final long serialVersionUID = -8151757899967428436L;
	
	private Cliente cliente;
	
	private List<Cliente> listaCliente = null;
	
	public ClienteController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		cliente = (Cliente) flash.get("clienteFlash");
	}
	
	public List<Cliente> getListaCliente(){
		if (listaCliente == null) {
			ClienteDAO dao = new ClienteDAO();
			listaCliente = dao.findAll();
			if (listaCliente == null)
				listaCliente = new ArrayList<Cliente>();
			dao.closeConnection();
		}
		
		return listaCliente;
	}
	
	public void editar(int id) {
		ClienteDAO dao = new ClienteDAO();
		setCliente(dao.findById(id));
	}
	
	public void incluir() {
		ClienteDAO dao = new ClienteDAO();
		if (dao.create(getCliente())) {
			limpar();
			// para atualizar o data table
			listaCliente = null;
		}
		dao.closeConnection();
	}
	
	public void alterar() {
		ClienteDAO dao = new ClienteDAO();
		if (dao.update(getCliente())) {
			limpar();
			// para atualizar o data table
			listaCliente = null;
		}
		dao.closeConnection();
	}
	
	public void excluir() {
		ClienteDAO dao = new ClienteDAO();
		if (dao.delete(getCliente().getId())) {
			limpar();
			// para atualizar o data table
			listaCliente = null;
		}
		dao.closeConnection();
	}
	
	public Estado[] getListaEstado() {
		return Estado.values();
	}

	private void limpar() {
		cliente = null;
	}

	private Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
