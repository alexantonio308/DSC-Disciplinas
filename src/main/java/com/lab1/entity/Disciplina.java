package com.lab1.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Disciplina {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	private String nome;
	
    private double nota;
    private int likes;
    
	@OneToMany(mappedBy = "disciplina")
	private List<Nota> notas;
	
	public Disciplina(String nome, double nota, int likes) {
		super();
		this.nome = nome;
		this.nota = nota;
		this.likes = likes;
	}

    public Disciplina() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}
	public List<Nota> getNotas() {
		return notas;
	}
	public void addNota(Nota nota) {
		this.notas.add(nota);
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public long getId() {
		return id;
	}
    
    

}
