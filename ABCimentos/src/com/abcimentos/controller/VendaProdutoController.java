package com.abcimentos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abcimentos.application.Session;
import com.abcimentos.application.Util;
import com.abcimentos.dao.ProdutoDAO;
import com.abcimentos.model.ItemVenda;
import com.abcimentos.model.Produto;


@Named
@ViewScoped
public class VendaProdutoController  implements Serializable {

	private static final long serialVersionUID = 5537729380314258216L;

	private String nome;
	
	private List<Produto> listaProduto = null;
	
	public List<Produto> getListaProduto(){
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
	
	public void adicionar(int id) {
		// pesquisa o servico selecionado
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.findById(id);
		
		// verifica se existe o carrinho na sessao
		if (Session.getInstance().getAttribute("carrinho") == null) {
			// adiciona o carrinho na sessao
			Session.getInstance().setAttribute("carrinho", new ArrayList<ItemVenda>());
		}
		// busca o carrinho da sessao
		List<ItemVenda> carrinho =  
				(List<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		
		// cria um item de venda
		ItemVenda item = new ItemVenda();
		item.setProduto(produto);
		item.setValor(produto.getValor());
		
		// adiciona o item no objeto de referencia do carrinho
		carrinho.add(item);
		
		// atualiza o carrinho
		Session.getInstance().setAttribute("carrinho", carrinho);
		
		Util.addMessageError("Adicionado com Sucesso! Quantidade de itens: "+carrinho.size());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
}
