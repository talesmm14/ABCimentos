package com.abcimentos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abcimentos.application.Util;
import com.abcimentos.dao.ClienteDAO;
import com.abcimentos.model.Cliente;

@Named
@ViewScoped
public class ConsultaClienteController  implements Serializable {
	
	private static final long serialVersionUID = -7476359732900236092L;
	
	private String nome;
	
	private List<Cliente> listaCliente = null;

	public List<Cliente> getListaCliente() {
		if (listaCliente == null) {
			ClienteDAO dao = new ClienteDAO();
			listaCliente = dao.findByNome(getNome());
			if (listaCliente == null)
				listaCliente = new ArrayList<Cliente>();
			dao.closeConnection();
		}
		return listaCliente;
	}
	
	public void pesquisar() {
		listaCliente = null;
	}
	
	public void editar(int id) {
		ClienteDAO dao = new ClienteDAO();
		Cliente cliente = dao.findById(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("clienteFlash", cliente);
		Util.redirect("cadastroClientes.xhtml");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
