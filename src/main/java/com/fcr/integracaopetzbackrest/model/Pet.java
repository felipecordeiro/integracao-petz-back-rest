package com.fcr.integracaopetzbackrest.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
public class Pet extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String nome;

	@NotEmpty
	private String especie;

	@NotEmpty
	private String raca;

	@NotEmpty
	private String sexo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
