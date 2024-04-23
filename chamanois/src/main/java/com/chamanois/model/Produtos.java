package com.chamanois.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Produtos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduto;
	private String nomeProduto;
	@NumberFormat(pattern = "#0.00")
	private double valorProduto;
	private String descricaoProduto;
	private int avaliacaoProduto;
	private String imgUrl;
	
	
	@ManyToMany
	@JoinTable(name = "usuario_produto", joinColumns = @JoinColumn(name = "id_produto"), inverseJoinColumns = @JoinColumn(name = "id_usuario"))
	private Set<Usuarios> usuarios = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "empresa_produto", joinColumns = @JoinColumn(name = "id_produto"), inverseJoinColumns = @JoinColumn(name = "id_empresa"))
	private Set<Empresas> empresas = new HashSet<>();

	public Produtos() {

	}

	public Produtos(Long idProduto, String nomeProduto, double valorProduto, String descricaoProduto,
			int avaliacaoProduto, String imgUrl, Set<Usuarios> usuarios, Set<Empresas> empresas) {
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
		this.valorProduto = valorProduto;
		this.descricaoProduto = descricaoProduto;
		this.avaliacaoProduto = avaliacaoProduto;
		this.imgUrl = imgUrl;
		this.usuarios = usuarios;
		this.empresas = empresas;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public double getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(double valorProduto) {
		this.valorProduto = valorProduto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public int getAvaliacaoProduto() {
		return avaliacaoProduto;
	}

	public void setAvaliacaoProduto(int avaliacaoProduto) {
		this.avaliacaoProduto = avaliacaoProduto;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Set<Usuarios> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Empresas> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Set<Empresas> empresas) {
		this.empresas = empresas;
	}

}
