package com.abcimentos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abcimentos.application.Util;
import com.abcimentos.dao.ProdutoDAO;
import com.abcimentos.model.Produto;

@Named
@ViewScoped
public class ConsultaProdutosController  implements Serializable {

	private static final long serialVersionUID = 5623081009169850829L;
	
	private String nome;
	
	private List<Produto> listaProduto = null;

	public List<Produto> getListaCliente() {
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findByNome(getNome());
			if (listaProduto == null)
				listaProduto = new ArrayList<Produto>();
			dao.closeConnection();
		}
		return listaProduto;
	}
	
	public void pesquisar() {
		listaProduto = null;
	}
	
	public void editar(int id) {
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.findById(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("produtoFlash", produto);
		Util.redirect("produto.xhtml");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
