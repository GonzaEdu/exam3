package com.cibertec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "HistorialMedico")
public class HistorialMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHistorial;
    
    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;
    
    private java.sql.Date fechaConsulta;
    private String diagnostico;
    private String tratamiento;
    
    @Override
    public String toString() {
		return "Subject [idHistorial=" + idHistorial + ", paciente=" + paciente + ", fechaConsulta=" + fechaConsulta + ", diagnostico="+diagnostico + ", tratamiento=" + tratamiento + "]";
	}

    // Getters y Setters
    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public java.sql.Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(java.sql.Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}

