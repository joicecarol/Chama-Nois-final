package com.chamanois.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chamanois.model.Empresas;
import com.chamanois.model.Produtos;
import com.chamanois.services.EmpresasService;
import com.chamanois.services.ProdutosService;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private ProdutosService produtosService;

	@Autowired
	private EmpresasService empresasService;

	@GetMapping
	public String listProdutos(Model model) {
		List<Produtos> produtos = produtosService.getAllProdutos();
		model.addAttribute("produtos", produtos);
		return "pages/produtos/listarProdutos";
	}

	@GetMapping("/novo")
	public String showFormForAdd(Model model) {
		Produtos produtos = new Produtos();
		List<Empresas> empresas = empresasService.getAllEmpresas();
		model.addAttribute("produtos", produtos);
		model.addAttribute("empresas", empresas);
		return "pages/produtos/formProdutos";
	}

	@PostMapping("/salvar")
	public String saveProduto(@ModelAttribute("produtos") Produtos produtos, @RequestParam Set<Long> empresas) {
		produtos.setEmpresas(empresas.stream().map(empresasService::getEmpresaById).collect(Collectors.toSet()));
		produtosService.saveProduto(produtos, empresas);
		return "redirect:/produtos";
	}

	@GetMapping("/editar/{id}")
	public String showFormForUpdate(@PathVariable Long id, Model model) {
		Produtos produtos = produtosService.getProdutoById(id);
		model.addAttribute("produtos", produtos);
		model.addAttribute("empresas", empresasService.getAllEmpresas());
		return "pages/produtos/atualizarProdutos";
	}

	@PostMapping("/editar/{id}")
	public String updateProduto(@PathVariable Long id, @ModelAttribute("produtos") Produtos produtos,
			@RequestParam Set<Long> empresas) {
		produtos.setEmpresas(empresas.stream().map(empresasService::getEmpresaById).collect(Collectors.toSet()));
		produtosService.updateProduto(id, produtos);
		return "redirect:/produtos";
	}

	@GetMapping("/deletar/{id}")
	public String deleteProduto(@PathVariable Long id) {
		produtosService.deleteProduto(id);
		return "redirect:/produtos";
	}
}
