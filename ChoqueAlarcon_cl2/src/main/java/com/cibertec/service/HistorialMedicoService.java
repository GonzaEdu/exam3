package com.cibertec.service;

import com.cibertec.model.HistorialMedico;
import com.cibertec.model.Paciente;
import com.cibertec.repository.IHistorialMedicoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoService {

    @Autowired
    private IHistorialMedicoInterface historialRepo;

    public HistorialMedico registrarHistorial(HistorialMedico historial) {
        return historialRepo.save(historial);
    }

    public List<HistorialMedico> listarHistoriales() {
        return historialRepo.findAll();
    }

    public List<HistorialMedico> listarHistorialesPorPaciente(Paciente paciente) {
        return historialRepo.findByPaciente(paciente);
    }
}
