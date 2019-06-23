package com.abcimentos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abcimentos.application.Session;
import com.abcimentos.application.Util;
import com.abcimentos.dao.VendaDAO;
import com.abcimentos.model.Usuario;
import com.abcimentos.model.Venda;


@Named
@ViewScoped
public class HistoricoController  implements Serializable {

	private static final long serialVersionUID = -2918780617650053071L;
	
	private List<Venda> listaVenda;
	
	
	/**
	 * Este metodo retorna todas a vendas do usuario logado
	 * @return List<Venda>
	 */
	public List<Venda> getListaVenda(){
		if (listaVenda == null) {
			Usuario usuario = (Usuario)Session.getInstance().getAttribute("usuarioLogado");
			if (usuario == null) {
				listaVenda = new ArrayList<Venda>();
				return listaVenda;
			}
			VendaDAO dao = new VendaDAO();
			// buscando todas as vendas do usuario logado
			listaVenda = dao.findAll(usuario);
			// se o retorno da consulta for nula ... inicializar a lista para evitar erro de nullpointer
			if (listaVenda == null) 
				listaVenda = new ArrayList<Venda>();
		}
		return listaVenda;
	}
	
	public void detalhes(Venda venda) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("vendaFlash", venda);
		Util.redirect("/ABCimentos/faces/pages/detalhesvenda.xhtml");
	}
	
	
}
