package com.lab1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab1.entity.Disciplina;

@Repository
public interface DisciplinaDAO extends JpaRepository<Disciplina, Long> {

}
