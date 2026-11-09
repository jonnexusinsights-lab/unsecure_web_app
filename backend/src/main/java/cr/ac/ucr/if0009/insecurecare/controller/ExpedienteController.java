package cr.ac.ucr.if0009.insecurecare.controller;

import cr.ac.ucr.if0009.insecurecare.dto.ExpedienteDto;
import cr.ac.ucr.if0009.insecurecare.service.ExpedienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expedientes")
public class ExpedienteController {

    private final ExpedienteService expedienteService;

    public ExpedienteController(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    // [VULNERABILIDAD INTENCIONADA: A01:2021 - Broken Access Control]
    // Expone la lista global de expedientes medicos sin requerir roles ni comprobar permisos
    @GetMapping
    public ResponseEntity<List<ExpedienteDto>> obtenerTodos() {
        return ResponseEntity.ok(expedienteService.listarTodos());
    }

    // [VULNERABILIDAD INTENCIONADA: A01:2021 - IDOR (Insecure Direct Object Reference)]
    // Permite que un atacante acceda al expediente de cualquier paciente con solo cambiar el {id}
    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(expedienteService.obtenerPorId(id));
    }

    // [VULNERABILIDAD INTENCIONADA: A03:2021 - SQL Injection (SQLi)]
    // Endpoint vulnerable a inyeccion SQL mediante concatenacion en el parametro 'termino'
    // Ejemplo de exploit: /api/expedientes/buscar?termino=' OR '1'='1
    @GetMapping("/buscar")
    public ResponseEntity<List<ExpedienteDto>> buscarPorTermino(@RequestParam("termino") String termino) {
        return ResponseEntity.ok(expedienteService.buscarPorTermino(termino));
    }

    // [VULNERABILIDAD INTENCIONADA: A03:2021 - Stored XSS]
    // Acepta HTML/JavaScript en observacionesHtml sin validacion de sanidad
    @PostMapping
    public ResponseEntity<ExpedienteDto> crear(@RequestBody ExpedienteDto dto) {
        ExpedienteDto creado = expedienteService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // [VULNERABILIDAD INTENCIONADA: A01:2021 - IDOR en Modificacion]
    @PutMapping("/{id}")
    public ResponseEntity<ExpedienteDto> actualizar(@PathVariable Long id, @RequestBody ExpedienteDto dto) {
        return ResponseEntity.ok(expedienteService.actualizar(id, dto));
    }
}

