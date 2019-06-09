package com.abcimentos.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.abcimentos.application.Session;
import com.abcimentos.application.Util;
import com.abcimentos.dao.UsuarioDAO;
import com.abcimentos.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;
	
	public void entrar() {
		UsuarioDAO dao = new UsuarioDAO();
		// gerando o hash da senha informada na tela de login
		String senhaEncriptada = Util.encrypt(getUsuario().getSenha());
		
		System.out.print(getUsuario().toString());
		
		Usuario usuLogado = dao.findUsuario(getUsuario().getLogin(), senhaEncriptada);
		
		System.out.print("Passou aqui");
		
		// comparando os dados da tela de login com o banco de dados
		if (usuLogado != null) {
			Session.getInstance().setAttribute("usuarioLogado", usuLogado);
			// login valido
			Util.redirect("menu.xhtml");
			
			System.out.print("Entrou");
		} else 
			Util.addMessageError("Usuário ou senha inválido.");
		
	}
	
	public void limpar() {
		setUsuario(null);
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
