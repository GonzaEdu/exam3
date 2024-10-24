package com.cibertec.service;

import com.cibertec.model.Paciente;
import com.cibertec.repository.IPacienteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private IPacienteInterface repos;

    public Paciente registrarPaciente(Paciente paciente) {
        return repos.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return repos.findAll();
    }
    
    public Paciente buscarPorId(int id) {
        return repos.findById(id).orElse(null);
    }
}
