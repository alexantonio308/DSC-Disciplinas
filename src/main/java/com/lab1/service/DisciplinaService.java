package com.lab1.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab1.dao.ComentarioDAO;
import com.lab1.dao.DisciplinaDAO;
import com.lab1.dao.NotaDAO;
import com.lab1.dto.ComentarioDTO;
import com.lab1.dto.DisciplinaDTO;
import com.lab1.dto.NotaDTO;
import com.lab1.entity.Comentario;
import com.lab1.entity.Disciplina;
import com.lab1.entity.Nota;


@Service
public class DisciplinaService {
	@Autowired
	private DisciplinaDAO repositoryDisciplina;	
	
	@Autowired
	private NotaDAO repositoryNota;	
	
	@Autowired
	private ComentarioDAO repositoryComentario;	
	
    public Disciplina criarDisciplina(DisciplinaDTO disciplina){
        Disciplina disciplina1 = new Disciplina(disciplina.getNome(), disciplina.getNota(), disciplina.getLikes());
        repositoryDisciplina.save(disciplina1);
        return disciplina1;
    }
    public DisciplinaService() {
    	
    }
    @PostConstruct
    public void initDisciplinas() {
    	ObjectMapper mapper = new ObjectMapper();
    	TypeReference<List<Disciplina>> typeReference = new TypeReference<List<Disciplina>>(){};
		InputStream inputStream = ObjectMapper.class.getResourceAsStream("/json/disciplinasSI.json");
    	try {
    		List<Disciplina> disciplina = mapper.readValue(inputStream, new TypeReference<List<Disciplina>>(){});
    		this.repositoryDisciplina.saveAll(disciplina);
    		System.out.println("Disciplina Salva no BD !");
    	}catch(IOException e) {
    		System.out.println("Nao foi possivel salvar disciplinas: " + e.getMessage());
    	}
    }

    public List<Disciplina> buscarDisciplinas(){
        return repositoryDisciplina.findAll();
    }
    public Optional<Disciplina> buscarDisciplinasId(Long id){
    	return repositoryDisciplina.findById(id);
    }
    public Disciplina likedDisciplina(Long id){
    	if(this.repositoryDisciplina.existsById(id)){
    		Disciplina disciplina = this.repositoryDisciplina.findById(id).get();
			disciplina.setLikes(disciplina.getLikes() + 1);
			return this.repositoryDisciplina.save(disciplina);
    	}
		System.out.println("Disciplina" + id + " foi encontrada!");
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND); 
    }
	public Disciplina adicionarNota(Long id, NotaDTO notaDto) {
		if(this.repositoryDisciplina.existsById(id)) {
			Disciplina disciplina = this.repositoryDisciplina.findById(id).get();
			if(disciplina.getNotas().isEmpty()) {
				disciplina.setNota(notaDto.getNota());
				disciplina.addNota(repositoryNota.save(new Nota(notaDto.getNota(), disciplina)));
				repositoryDisciplina.save(disciplina);
				return disciplina;
			}
			Nota ultima = disciplina.getNotas().get(disciplina.getNotas().size() - 1);
			double media = (ultima.getNota() + notaDto.getNota()) / 2;
			disciplina.setNota(media);
			disciplina.addNota(repositoryNota.save(new Nota(notaDto.getNota(), disciplina)));
			repositoryDisciplina.save(disciplina);
			return disciplina;
		}
		System.out.println("Disciplina " + id + " não foi encontrada!");
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}
	public Disciplina adicionarComentario(Long id, ComentarioDTO comentarioDto) {
		if(this.repositoryDisciplina.existsById(id)) {
			Disciplina disciplina = this.repositoryDisciplina.findById(id).get();
			
			disciplina.addComentario(repositoryComentario.save(new Comentario(comentarioDto.getComentario(), disciplina)));
			return this.repositoryDisciplina.save(disciplina);
		}
		System.out.println("Disciplina " + id + " foi encontrada!");
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND); 
	}
	
    public List<Disciplina> getRankingNotesDesc() {
        return this.repositoryDisciplina.findByOrderByNotaDesc();
    }

    public List<Disciplina> getRankingLikesDesc() {
        return repositoryDisciplina.findAll(Sort.by(Sort.Direction.DESC, "likes"));
    }

}
