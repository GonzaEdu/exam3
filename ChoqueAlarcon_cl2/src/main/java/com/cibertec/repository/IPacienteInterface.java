package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Paciente;

public interface IPacienteInterface extends JpaRepository<Paciente, Integer> {
	

}


