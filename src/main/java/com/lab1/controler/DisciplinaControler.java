package com.lab1.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.lab1.dto.ComentarioDTO;
import com.lab1.dto.DisciplinaDTO;
import com.lab1.dto.NotaDTO;
import com.lab1.entity.Disciplina;
import com.lab1.service.DisciplinaService;

@RestController
public class DisciplinaControler {

    private DisciplinaService service;
    
    public DisciplinaControler(DisciplinaService service) {
		super();
		this.service = service;
	}
    
	@PostMapping("/disciplinas")
	public ResponseEntity<Disciplina> criarDisciplinaa(@RequestBody DisciplinaDTO disciplina) {
		return new ResponseEntity<Disciplina>(service.criarDisciplina(disciplina),HttpStatus.CREATED);
	}
	@GetMapping("/disciplinas")
	public ResponseEntity<List<Disciplina>> buscarDisciplinas() {
		return new ResponseEntity<List<Disciplina>>(service.buscarDisciplinas(), HttpStatus.OK);
	}
	
	@GetMapping("/disciplinas/{id}")
	private ResponseEntity<Disciplina> buscarDisciplinasId(@PathVariable Long id){
		Optional<Disciplina> disciplina = service.buscarDisciplinasId(id);
		if(disciplina.isPresent())
			return new ResponseEntity<Disciplina>(disciplina.get(),HttpStatus.OK);
		return new ResponseEntity<Disciplina>(HttpStatus.NOT_FOUND);
	}
	@PostMapping("/disciplinas/likes/{id}")
	public ResponseEntity<Disciplina> likedDisciplina(@PathVariable Long id){
		Optional<Disciplina> disciplina = service.buscarDisciplinasId(id);
		if(disciplina.isPresent())	
			return new ResponseEntity<Disciplina>(service.likedDisciplina(id),HttpStatus.OK);
		return new ResponseEntity<Disciplina>(HttpStatus.NOT_FOUND);
	}
	@PostMapping("/disciplinas/nota/{id}")
	public ResponseEntity<Disciplina> adicionarNota(@PathVariable Long id, @RequestBody NotaDTO notaDto){
		Optional<Disciplina> disciplina = service.buscarDisciplinasId(id);
		if(disciplina.isPresent())	
			return new ResponseEntity<Disciplina>(service.adicionarNota(id, notaDto), HttpStatus.OK);
		return new ResponseEntity<Disciplina>(HttpStatus.NOT_FOUND);
	}
	@PostMapping("/disciplinas/comentario/{id}")
	public ResponseEntity<Disciplina> adicionarComentario(@PathVariable Long id, @RequestBody ComentarioDTO comentarioDto){
		try {
			return new ResponseEntity<Disciplina>(service.adicionarComentario(id, comentarioDto), HttpStatus.OK);
		}catch(HttpClientErrorException e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND); 
		}
	}
    @GetMapping("/ranking/likes")
    public ResponseEntity<List<Disciplina>> getRankingLikesDesc() {
        return new ResponseEntity<>(service.getRankingLikesDesc(), HttpStatus.OK);
    }
    @GetMapping("/ranking/notas")
    public ResponseEntity<List<Disciplina>> getRankingNotesDesc() {
        return new ResponseEntity<>(service.getRankingNotesDesc(), HttpStatus.OK);
    }


}
