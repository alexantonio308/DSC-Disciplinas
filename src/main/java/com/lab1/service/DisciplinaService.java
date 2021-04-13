package com.lab1.service;

import com.lab1.dto.DisciplinaDTO;
import com.lab1.entity.Disciplina;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DisciplinaService {
    List<Disciplina> disciplinas = new ArrayList<>();
    static private int incrementador = 1;

    public Disciplina criarDisciplina(DisciplinaDTO disciplina){
        Disciplina disciplina1 = new Disciplina(incrementador++, disciplina.getNome(), disciplina.getNota());
        this.disciplinas.add(disciplina1);

        return disciplina1;
    }

    public List<Disciplina> buscarDisciplinas(){
        return this.disciplinas;
    }

    public Disciplina buscarPeloId(int id){
        for(Disciplina disciplinaSetada: this.disciplinas){
            if (disciplinaSetada.getId() == id){
                return disciplinaSetada;
            }
        }
        return null;
    }

    public Disciplina removeDisciplina(int id){
        for(Disciplina disciplinaExcluida: this.disciplinas){
            if (disciplinaExcluida.getId() == id){
                this.disciplinas.remove(disciplinaExcluida);
            }
        }
        return null;
    }

    public List<Disciplina> retornaRanking() {
        List<Disciplina> disciplinasRanking = this.disciplinas;
        disciplinasRanking.sort((d1,d2) -> Double.compare(d1.getNota(),d2.getNota()));

        return disciplinasRanking;
    }

    public Disciplina mudarNome (int id, String name) {
        for (Disciplina disciplinaNome : this.disciplinas) {
            if (disciplinaNome.getId() == id) {
                disciplinaNome.setNome(name);

                return disciplinaNome;
            }
        }
        return null;
    }

    public Disciplina mudarNota (int id, Double nota){
        for(Disciplina disciplinaNota: this.disciplinas){
            if (disciplinaNota.getId() == id){
                disciplinaNota.setNota(nota);

                return disciplinaNota;
            }
        }
        return null;
    }
}
