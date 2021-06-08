package com.lab1.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab1.dao.DisciplinaDAO;
import com.lab1.dao.NotaDAO;
import com.lab1.dto.DisciplinaDTO;
import com.lab1.dto.NotaDTO;
import com.lab1.entity.Disciplina;
import com.lab1.entity.Nota;


@Service
public class DisciplinaService {
	@Autowired
	private DisciplinaDAO repositoryDisciplina;	
	
	@Autowired
	private NotaDAO repositoryNota;	
	
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
		Disciplina disciplina = this.repositoryDisciplina.findById(id).get();
		disciplina.setNota(notaDto.getNota());
		disciplina.addNota(repositoryNota.save(new Nota(notaDto.getNota(), disciplina)));
		repositoryDisciplina.save(disciplina);
		return disciplina;
	}
//
//    public Disciplina removeDisciplina(int id){
//        for(Disciplina disciplinaExcluida: this.disciplinas){
//            if (disciplinaExcluida.getId() == id){
//                this.disciplinas.remove(disciplinaExcluida);
//            }
//        }
//        return null;
//    }
//
//    public List<Disciplina> retornaRanking() {
//        List<Disciplina> disciplinasRanking = this.disciplinas;
//        disciplinasRanking.sort((d1,d2) -> Double.compare(d1.getNota(),d2.getNota()));
//
//        return disciplinasRanking;
//    }
//
//    public Disciplina mudarNome (int id, String name) {
//        for (Disciplina disciplinaNome : this.disciplinas) {
//            if (disciplinaNome.getId() == id) {
//                disciplinaNome.setNome(name);
//
//                return disciplinaNome;
//            }
//        }
//        return null;
//    }
//
//    public Disciplina mudarNota (int id, Double nota){
//        for(Disciplina disciplinaNota: this.disciplinas){
//            if (disciplinaNota.getId() == id){
//                disciplinaNota.setNota(nota);
//
//                return disciplinaNota;
//            }
//        }
//        return null;
//    }

}
