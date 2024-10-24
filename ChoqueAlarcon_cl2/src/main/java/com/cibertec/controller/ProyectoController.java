package com.cibertec.controller;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.cibertec.model.Paciente;
import com.cibertec.model.HistorialMedico;
import com.cibertec.service.PacienteService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.cibertec.service.HistorialMedicoService;

import java.util.HashMap;
import java.util.List;

@Controller
public class ProyectoController {

    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private HistorialMedicoService historialMedicoService;

    @GetMapping("/registrarPaciente")
    public String registrarPaciente(
            @RequestParam(name = "nombre", required = false, defaultValue = "Paciente") String nombre,
            @RequestParam(name = "apellido", required = false, defaultValue = "Apellido") String apellido,
            @RequestParam(name = "fechaNacimiento", required = false, defaultValue = "2000-01-01") String fechaNacimiento,
            @RequestParam(name = "genero", required = false, defaultValue = "Otro") String genero,
            @RequestParam(name = "telefono", required = false) String telefono,
            @RequestParam(name = "email", required = false) String email,
            Model model) {
        
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimiento)); 
        paciente.setGenero(genero);
        paciente.setTelefono(telefono);
        paciente.setEmail(email);
        
        paciente = pacienteService.registrarPaciente(paciente);
        
        model.addAttribute("paciente", paciente);
        
        generarConstanciaPDF(paciente);
        
        return "registroPacienteExitoso";
    }

    @GetMapping("/listarPacientes")
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        model.addAttribute("listaPacientes", pacientes);
        return "listadoPacientes";
    }
    
    @GetMapping("/registrarAtencion")
    public String registrarAtencion(
            @RequestParam(name = "idPaciente") int idPaciente,
            @RequestParam(name = "fechaConsulta") String fechaConsulta,
            @RequestParam(name = "diagnostico") String diagnostico,
            @RequestParam(name = "tratamiento") String tratamiento,
            Model model) {
        
        Paciente paciente = pacienteService.buscarPorId(idPaciente);
        HistorialMedico historial = new HistorialMedico();
        historial.setPaciente(paciente);
        historial.setFechaConsulta(java.sql.Date.valueOf(fechaConsulta));
        historial.setDiagnostico(diagnostico);
        historial.setTratamiento(tratamiento);
        
        historial = historialMedicoService.registrarHistorial(historial);
        
        model.addAttribute("historial", historial);
        
        return "registroAtencionExitoso";
    }

    @GetMapping("/listarHistorialPorPaciente")
    public String listarHistorialPorPaciente(@RequestParam(name = "idPaciente") int idPaciente, Model model) {
        Paciente paciente = pacienteService.buscarPorId(idPaciente);
        List<HistorialMedico> historiales = historialMedicoService.listarHistorialesPorPaciente(paciente);
        model.addAttribute("listaHistorial", historiales);
        return "listadoHistorial";
    }

    private void generarConstanciaPDF(Paciente paciente) {
        try {
            // Cargar el archivo Jasper
            File file = new ClassPathResource("constanciaPaciente.jasper").getFile();
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            // Crear parámetros para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nombre", paciente.getNombre());
            parameters.put("apellido", paciente.getApellido());
            parameters.put("fechaNacimiento", paciente.getFechaNacimiento());

            // Crear un DataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(paciente));

            // Llenar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar el reporte a PDF
            String outputPath = "constancia_" + paciente.getApellido() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

        } catch (Exception e) {
            e.printStackTrace(); // Manejar adecuadamente el error
        }
    }
}


