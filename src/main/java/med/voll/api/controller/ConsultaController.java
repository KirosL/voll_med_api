package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")

public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Registra una consulta en la base de datos",
            description = "",
            tags = {"consulta","post"}
    )
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){

        var response = service.agendar(datos);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    @Operation(
            summary = "Cancela una consulta en la base de datos",
            description = "",
            tags = {"consulta","delete"}
    )
    public ResponseEntity cancelar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.cancelar(paciente.getMotivoCancelacion());
        return ResponseEntity.noContent().build();
    }

}