package com.lab1.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@AllArgsConstructor
public class Nota {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	private double nota;

	@ManyToOne
	@JoinColumn(name = "disciplina_id")
	private Disciplina disciplina;
	
	public Nota() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Nota(double nota, Disciplina disciplina) {
		super();
		this.nota = nota;
		this.disciplina = disciplina;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public long getId() {
		return id;
	}

}
