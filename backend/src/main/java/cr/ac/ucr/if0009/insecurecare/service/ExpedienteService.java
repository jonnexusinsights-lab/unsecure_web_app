package cr.ac.ucr.if0009.insecurecare.service;

import cr.ac.ucr.if0009.insecurecare.dto.ExpedienteDto;
import cr.ac.ucr.if0009.insecurecare.model.ExpedienteMedico;
import cr.ac.ucr.if0009.insecurecare.model.Usuario;
import cr.ac.ucr.if0009.insecurecare.repository.ExpedienteRepository;
import cr.ac.ucr.if0009.insecurecare.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ExpedienteService(ExpedienteRepository expedienteRepository, UsuarioRepository usuarioRepository) {
        this.expedienteRepository = expedienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // [VULNERABILIDAD INTENCIONADA: A01:2021 - Broken Access Control]
    // Retorna todos los expedientes confidenciales sin verificar el rol del usuario que solicita
    public List<ExpedienteDto> listarTodos() {
        return expedienteRepository.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    // [VULNERABILIDAD INTENCIONADA: A01:2021 - Broken Access Control / IDOR (BOLA)]
    // Permite que un paciente consulte el expediente de cualquier otro paciente alterando el ID
    // Omite intencionalmente la verificacion: expediente.getUsuario().getId().equals(usuarioAutenticado.getId())
    public ExpedienteDto obtenerPorId(Long id) {
        ExpedienteMedico expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con ID: " + id));
        return convertirADto(expediente);
    }

    // [VULNERABILIDAD INTENCIONADA: A03:2021 - SQL Injection (SQLi)]
    // Concatenacion cruda de parametros en consulta nativa SQL sin consultas preparadas ni parametros :termino
    @SuppressWarnings("unchecked")
    public List<ExpedienteDto> buscarPorTermino(String termino) {
        // Concatenacion vulnerable directa
        String sql = "SELECT * FROM EXPEDIENTES_MEDICOS WHERE diagnostico LIKE '%" + termino 
                   + "%' OR tratamiento LIKE '%" + termino + "%'";
        
        System.out.println("[AUDITORIA FORENSE] Ejecutando consulta SQL vulnerable: " + sql);

        List<ExpedienteMedico> resultados = entityManager.createNativeQuery(sql, ExpedienteMedico.class)
                .getResultList();

        return resultados.stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    // [VULNERABILIDAD INTENCIONADA: A03:2021 - Cross-Site Scripting (XSS Almacenado)]
    // Almacena contenido HTML/JavaScript arbitrario sin sanitizacion en la base de datos
    @Transactional
    public ExpedienteDto crear(ExpedienteDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        ExpedienteMedico expediente = new ExpedienteMedico();
        expediente.setUsuario(usuario);
        expediente.setNumeroExpediente("EXP-2026-" + System.currentTimeMillis() % 10000);
        expediente.setDiagnostico(dto.getDiagnostico());
        expediente.setTratamiento(dto.getTratamiento());
        // Inyeccion directa de HTML/JS inseguro
        expediente.setObservacionesHtml(dto.getObservacionesHtml());
        expediente.setConfidencial(dto.getConfidencial() != null ? dto.getConfidencial() : true);

        ExpedienteMedico guardado = expedienteRepository.save(expediente);
        return convertirADto(guardado);
    }

    // [VULNERABILIDAD INTENCIONADA: A01:2021 - IDOR en Modificacion]
    // Modifica expedientes ajenos sin validar permisos
    @Transactional
    public ExpedienteDto actualizar(Long id, ExpedienteDto dto) {
        ExpedienteMedico expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con ID: " + id));

        expediente.setDiagnostico(dto.getDiagnostico());
        expediente.setTratamiento(dto.getTratamiento());
        expediente.setObservacionesHtml(dto.getObservacionesHtml());
        expediente.setConfidencial(dto.getConfidencial());

        ExpedienteMedico actualizado = expedienteRepository.save(expediente);
        return convertirADto(actualizado);
    }

    private ExpedienteDto convertirADto(ExpedienteMedico entidad) {
        ExpedienteDto dto = new ExpedienteDto();
        dto.setId(entidad.getId());
        dto.setUsuarioId(entidad.getUsuario().getId());
        dto.setNombrePaciente(entidad.getUsuario().getNombreCompleto());
        dto.setNumeroExpediente(entidad.getNumeroExpediente());
        dto.setDiagnostico(entidad.getDiagnostico());
        dto.setTratamiento(entidad.getTratamiento());
        dto.setObservacionesHtml(entidad.getObservacionesHtml());
        dto.setFechaRegistro(entidad.getFechaRegistro());
        dto.setConfidencial(entidad.getConfidencial());
        return dto;
    }
}

