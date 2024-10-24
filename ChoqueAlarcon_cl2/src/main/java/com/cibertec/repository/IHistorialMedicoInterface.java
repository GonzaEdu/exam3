package com.cibertec.repository;

import com.cibertec.model.HistorialMedico;
import com.cibertec.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHistorialMedicoInterface extends JpaRepository<HistorialMedico, Integer> {
    List<HistorialMedico> findByPaciente(Paciente paciente);
}
