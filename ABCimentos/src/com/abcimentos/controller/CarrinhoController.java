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
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = 8149999991497336066L;

	private Venda venda;

	private Cliente cliente;

	public void remover(int idvenda) {
		// pesquisa o produto selecionado
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.findById(idvenda);

		// verifica se existe o carrinho na sessao
		if (Session.getInstance().getAttribute("carrinho") == null) {
			// adiciona o carrinho na sessao
			Session.getInstance().setAttribute("carrinho", new ArrayList<ItemVenda>());
		}
		// busca o carrinho da sessao
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		
		System.out.println("1." +idvenda);
		
		//procura e remove o item do carrinho
		for (ItemVenda itemVenda : carrinho) {
			System.out.println("2." + itemVenda.getId());
			if (itemVenda.getId() == idvenda) {
				System.out.println("3." +itemVenda.getId());
				carrinho.remove(itemVenda);
				break;
			}
		}
		
		// atualiza o carrinho
		Session.getInstance().setAttribute("carrinho", carrinho);

		Util.addMessageError("Item removido com Sucesso! ");
	}

	public void finalizar() {
		System.out.println(cliente.getNome());
		getVenda().setCliente(cliente.getNome());
		getVenda().setUsuario((Usuario) Session.getInstance().getAttribute("usuarioLogado"));
		VendaDAO dao = new VendaDAO();
		dao.create(getVenda());
	}

	public Venda getVenda() {
		if (venda == null) {
			venda = new Venda();
		}
		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		if (carrinho != null)
			venda.setListaItemVenda(carrinho);
		else
			venda.setListaItemVenda(new ArrayList<ItemVenda>());

		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
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
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");

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

	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
