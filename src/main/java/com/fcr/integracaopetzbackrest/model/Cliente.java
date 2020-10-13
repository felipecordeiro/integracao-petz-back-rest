package com.fcr.integracaopetzbackrest.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
public class Cliente extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String nome;

	@NotEmpty
	private String sobrenome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobreNome) {
		this.sobrenome = sobreNome;
	}
}
