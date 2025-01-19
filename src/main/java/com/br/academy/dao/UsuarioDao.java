package com.br.academy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.academy.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {

	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
	public Usuario findByEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.user = :user AND u.senha = :senha")
	public Usuario buscarLogin(String user, String senha);
}
