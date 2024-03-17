package br.com.springboot.projeto.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@SequenceGenerator(name = "seqCliente", sequenceName = "seqCliente", allocationSize = 1, initialValue = 1)
@EnableAutoConfiguration
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCliente")
    private Long id;

    private String nome;
    private String endereco;
    private String bairro;

    @ElementCollection
    @CollectionTable(name = "telefones", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "numero")
    private List<String> telefones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}
	
}
