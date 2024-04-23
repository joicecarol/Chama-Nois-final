package com.chamanois.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chamanois.dto.UsuariosDTO;
import com.chamanois.model.Usuarios;
import com.chamanois.services.UsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private UsuariosService usuariosService;

	@GetMapping
	public String userList(Model model) {
		List<UsuariosDTO> usuario = usuariosService.findAllUsuarios();
		model.addAttribute("usuarios", usuario);
		return "pages/usuarios/listarUsuarios";
	}

	@GetMapping("/novo")
	public String showUserForm(Model model) {
		UsuariosDTO usuario = new UsuariosDTO();
		model.addAttribute("usuarios", usuario);
		return "pages/usuarios/cadastro";
	}

	@PostMapping("/novo/salvar")
	public String saveUsuario(@ModelAttribute("senhaUsuario") UsuariosDTO usuariosDTO, BindingResult result, Model model) {
		Usuarios existingUser = usuariosService.findUserByEmail(usuariosDTO.getEmailUsuario());

		if (existingUser != null && existingUser.getEmailUsuario() != null
				&& !existingUser.getEmailUsuario().isEmpty()) {
			result.rejectValue("emailUsuario", null, "Esse email já está registrado.");
		}

		if (result.hasErrors()) {
			model.addAttribute("usuarios", usuariosDTO);
			return "/novo";
		}

		usuariosService.saveUsuario(usuariosDTO);
		return "redirect:/login";
	}
	
	@GetMapping("/editar/{id}")
	public String showFormForUpdate(@PathVariable Long id, Model model) {
		Usuarios usuarios = usuariosService.findUserById(id);
		model.addAttribute("usuarios", usuarios);
		return "pages/usuarios/atualizarUsuarios";
	}

	@PostMapping("/editar/{id}")
	public String updateUsuario(@PathVariable Long id, @ModelAttribute("usuarios") Usuarios usuarios) {
		usuariosService.updateUsuario(id, usuarios);
		return "redirect:/usuarios";
	}
	
	@GetMapping("/deletar/{id}")
	public String deleteUsuario(@PathVariable Long id) {
		usuariosService.deleteUsuario(id);
		return "redirect:/usuarios";
	}
}
