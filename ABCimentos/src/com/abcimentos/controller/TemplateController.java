package com.abcimentos.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abcimentos.application.Session;
import com.abcimentos.application.Util;
import com.abcimentos.model.ItemVenda;
import com.abcimentos.model.Usuario;


@Named
@ViewScoped
public class TemplateController implements Serializable {
	
	private static final long serialVersionUID = -3540211319186607809L;
	
	Usuario usuarioLogado = null;
	
	int qtdItensCarrinho;
	
	public TemplateController() {
		usuarioLogado = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
	}
	
	public void encerrarSessao() {
		Session.getInstance().invalidateSession();
		Util.redirect("/ABCimentos/faces/login.xhtml");
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public int getQtdItensCarrinho() {
		qtdItensCarrinho = 0;
		List<ItemVenda> itens = (List<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		if (itens != null)
			qtdItensCarrinho = itens.size();
		return qtdItensCarrinho;	
		
	}
	
	public void redirecionar(String pagina) {
		Util.redirect(pagina);
	}
	
	
}
