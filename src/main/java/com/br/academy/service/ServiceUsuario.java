package com.br.academy.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.academy.Exception.ServiceExc;
import com.br.academy.Exceptions.CriptoExistException;
import com.br.academy.Exceptions.EmailExistsException;
import com.br.academy.dao.UsuarioDao;
import com.br.academy.model.Usuario;
import com.br.academy.util.Util;

@Service
public class ServiceUsuario {
	
	@Autowired
	private UsuarioDao usuarioRepositorio;
	
	public void salvarUsuario(Usuario user) throws Exception {
		try {
			if(usuarioRepositorio.findByEmail(user.getEmail()) != null) {
				throw new EmailExistsException("Já Existe um email cadastrado para: " + user.getEmail());
			} else if(user.getUser().length() < 3 || user.getUser().length() > 20) {
				throw new Exception("Usuário deve conter entre 3 e 20 caracteres");
			} else if(user.getSenha().length() < 3) {
				throw new Exception("Senha deve conter pelo menos 3 caracteres");
			}
			user.setSenha(Util.md5(user.getSenha()));
		} catch (NoSuchAlgorithmException e) {
			throw new CriptoExistException("Erro na criptografia da senha");
		}
		
		usuarioRepositorio.save(user);
	}
	
	public Usuario loginUser(String user, String senha) throws ServiceExc{
		Usuario userLogin = usuarioRepositorio.buscarLogin(user, senha);
		return userLogin;
	}
}
