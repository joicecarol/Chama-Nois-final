package com.chamanois.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Empresas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmpresa;
	private String nomeEmpresa;
	private String cnpjEmpresa;
	private String enderecoEmpresa;
	private String telefoneEmpresa;
	
	@ManyToMany(mappedBy = "empresas", cascade = { CascadeType.ALL })
	private Set<Produtos> produtos = new HashSet<>();

	public Empresas() {

	}

	

	public Empresas(Long idEmpresa, String nomeEmpresa, String cnpjEmpresa, String enderecoEmpresa,
			String telefoneEmpresa, Set<Produtos> produtos) {
		this.idEmpresa = idEmpresa;
		this.nomeEmpresa = nomeEmpresa;
		this.cnpjEmpresa = cnpjEmpresa;
		this.enderecoEmpresa = enderecoEmpresa;
		this.telefoneEmpresa = telefoneEmpresa;
		this.produtos = produtos;
	}



	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getEnderecoEmpresa() {
		return enderecoEmpresa;
	}

	public void setEnderecoEmpresa(String enderecoEmpresa) {
		this.enderecoEmpresa = enderecoEmpresa;
	}

	public String getTelefoneEmpresa() {
		return telefoneEmpresa;
	}

	public void setTelefoneEmpresa(String telefoneEmpresa) {
		this.telefoneEmpresa = telefoneEmpresa;
	}

	public Set<Produtos> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<Produtos> produtos) {
		this.produtos = produtos;
	}

}
