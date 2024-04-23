package com.chamanois.servicesimpl;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chamanois.model.Produtos;
import com.chamanois.model.Empresas;
import com.chamanois.repositories.ProdutosRepository;
import com.chamanois.services.EmpresasService;
import com.chamanois.services.ProdutosService;

@Service
public class ProdutosServiceImpl implements ProdutosService {

	@Autowired
	private ProdutosRepository produtosRepository;

	@Autowired
	private EmpresasService empresasService;

	@Override
	public List<Produtos> getAllProdutos() {
		return produtosRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Produtos getProdutoById(Long idProduto) {
		return produtosRepository.findById(idProduto).orElse(null);
	}

	@Override
	@Transactional
	public Produtos saveProduto(Produtos produto, Set<Long> empresaIds) {
		Set<Empresas> empresas = empresaIds.stream().map(empresasService::getEmpresaById).filter(Objects::nonNull)
				.collect(Collectors.toSet());

		produto.setEmpresas(empresas);

		return produtosRepository.save(produto);
	}

	@Override
	@Transactional
	public Produtos updateProduto(Long idProduto, Produtos produtoAtualizado) {
		Produtos produtoExistente = produtosRepository.findById(idProduto).orElse(null);
		if (produtoExistente != null) {
			produtoExistente.setNomeProduto(produtoAtualizado.getNomeProduto());
			produtoExistente.setValorProduto(produtoAtualizado.getValorProduto());
			produtoExistente.setDescricaoProduto(produtoAtualizado.getDescricaoProduto());
			produtoExistente.setAvaliacaoProduto(produtoAtualizado.getAvaliacaoProduto());
			produtoExistente.setImgUrl(produtoAtualizado.getImgUrl());

			Set<Empresas> empresasAtualizadas = produtoAtualizado.getEmpresas();
			for (Empresas empresa : empresasAtualizadas) {
				empresa.getProdutos().add(produtoExistente);
			}

			produtoExistente.setEmpresas(empresasAtualizadas);

			return produtosRepository.save(produtoExistente);
		} else {
			throw new RuntimeException("Produto com o ID " + idProduto + " não foi encontrado.");
		}
	}

	@Override
	@Transactional
	public void deleteProduto(Long idProduto) {
		produtosRepository.deleteById(idProduto);
	}

}
