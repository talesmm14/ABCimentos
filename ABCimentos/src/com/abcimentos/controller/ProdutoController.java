package com.abcimentos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abcimentos.dao.ProdutoDAO;
import com.abcimentos.model.Produto;

@Named
@ViewScoped
public class ProdutoController  implements Serializable {

	private static final long serialVersionUID = 2425685236772104473L;

	private Produto produto;
	
	private List<Produto> listaProduto = null;
	
	public ProdutoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		produto = (Produto) flash.get("produtoFlash");
	}
	
	public List<Produto> getListaProduto(){
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findAll();
			if (listaProduto == null)
				listaProduto = new ArrayList<Produto>();
			dao.closeConnection();
		}
		
		return listaProduto;
	}
	
	public void editar(int id) {
		ProdutoDAO dao = new ProdutoDAO();
		setProduto(dao.findById(id));
	}
	
	public void incluir() {
		ProdutoDAO dao = new ProdutoDAO();
		if (dao.create(getProduto())) {
			limpar();
			// para atualizar o data table
			listaProduto = null;
		}
		dao.closeConnection();
	}
	
	public void alterar() {
		ProdutoDAO dao = new ProdutoDAO();
		if (dao.update(getProduto())) {
			limpar();
			// para atualizar o data table
			listaProduto = null;
		}
		dao.closeConnection();
	}
	
	public void excluir() {
		ProdutoDAO dao = new ProdutoDAO();
		if (dao.delete(getProduto().getId())) {
			limpar();
			// para atualizar o data table
			listaProduto = null;
		}
		dao.closeConnection();
	}


	private void limpar() {
		produto = null;
	}

	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	
}
