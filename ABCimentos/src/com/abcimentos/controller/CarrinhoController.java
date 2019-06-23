package com.abcimentos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.abcimentos.application.Session;
import com.abcimentos.application.Util;
import com.abcimentos.dao.ProdutoDAO;
import com.abcimentos.dao.VendaDAO;
import com.abcimentos.model.Cliente;
import com.abcimentos.model.ItemVenda;
import com.abcimentos.model.Produto;
import com.abcimentos.model.Usuario;
import com.abcimentos.model.Venda;


@Named
@RequestScoped
public class CarrinhoController  implements Serializable {

	private static final long serialVersionUID = 8149999991497336066L;

	private Venda venda;
	
	private String nome;
	
	public void remover(int id) {
		// implementar
		System.out.println("Implementar essa parte");
	}
	
	public void finalizar() {
		// implementar escolhendo o cliente no pedido
		getVenda().setCliente("teste");
		getVenda().setUsuario((Usuario)Session.getInstance().getAttribute("usuarioLogado"));
		VendaDAO dao = new VendaDAO();
		dao.create(getVenda());
	}
	
	public Venda getVenda() {
		if (venda == null) {
			venda = new Venda();
		}
		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = 
				(List<ItemVenda>)Session.getInstance().getAttribute("carrinho");
		
		if (carrinho != null)
			venda.setListaItemVenda(carrinho);
		else
			venda.setListaItemVenda(new ArrayList<ItemVenda>());
		
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	

	
	public void pedido(String nome1) {
		this.nome = nome1;
		System.out.println(nome);
		Util.redirect("vendaproduto.xhtml");
	}

	public void adicionar(int id) {
		// pesquisa o produto selecionado
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
		
		Util.addMessageError("Adicionado com Sucesso! ");
	}

	
}
