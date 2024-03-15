package br.com.springboot.projeto.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@SequenceGenerator(name = "seqTelefone", sequenceName = "seqTelefone", allocationSize = 1, initialValue = 1)
@EnableAutoConfiguration
public class Telefone {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTelefone")
    private Long id;

    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
