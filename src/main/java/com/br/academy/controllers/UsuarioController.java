package com.br.academy.controllers;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.academy.Exception.ServiceExc;
import com.br.academy.model.Aluno;
import com.br.academy.model.Usuario;
import com.br.academy.service.ServiceUsuario;
import com.br.academy.util.Util;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UsuarioController {
	
	@Autowired
	private ServiceUsuario serviceUsuario;
	
	@GetMapping("/")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Login/login");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home/index");
		mv.addObject("aluno", new Aluno());
		return mv;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.setViewName("Login/cadastro");
		return mv;
	}
	
	@PostMapping("salvarUsuario")
	public ModelAndView cadastrar(Usuario usuario) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			serviceUsuario.salvarUsuario(usuario);
			mv.setViewName("redirect:/");
		}catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("Login/cadastro");
		}
		return mv;
	}
	
	@PostMapping("login")
	public ModelAndView login(@Valid Usuario usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, ServiceExc{
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		if(br.hasErrors()) {
			mv.setViewName("Login/login");
		}
		Usuario userLogin = serviceUsuario.loginUser(usuario.getUser(), Util.md5(usuario.getSenha()));
		if(userLogin == null) {
			mv.addObject("msg", "Usuário não encontrado. Tente novamente");
			mv.setViewName("Login/login");
		} else {
			session.setAttribute("usuarioLogado", userLogin);
			return index();
		}
		return mv;
	}
	
	@PostMapping("logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return login();
	}
}
