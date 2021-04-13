package com.lab1.controler;

import com.lab1.dto.DisciplinaDTO;
import com.lab1.entity.Disciplina;
import com.lab1.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/disciplinas")
public class DisciplinaControler {

    @Autowired
    private DisciplinaService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<Disciplina> disciplinas(){
        return service.buscarDisciplinas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Disciplina postDisciplina (@RequestBody DisciplinaDTO disciplina){
        return service.criarDisciplina(disciplina);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private Disciplina deleteDisciplina (@PathVariable int id){
        return service.removeDisciplina(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Disciplina getDisciplinaId(@PathVariable int id){
        return service.buscarPeloId(id);
    }


    @PutMapping("/{id}/{nome}")
    @ResponseStatus(HttpStatus.CREATED)
    private Disciplina putNome(@PathVariable int id, @PathVariable String nome){
        return service.mudarNome(id, nome);
    }

    @PutMapping("/{id}/nota")
    @ResponseStatus(HttpStatus.CREATED)
    private Disciplina putNota (int id, double nota) {
        return service.mudarNota(id, nota);
    }

    @GetMapping("/ranking")
    @ResponseStatus(HttpStatus.OK)
    private List<Disciplina> getRanking(){
        return service.retornaRanking();
    }





}
