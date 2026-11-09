package cr.ac.ucr.if0009.insecurecare.dto;

import java.time.LocalDateTime;

public class ExpedienteDto {
    private Long id;
    private Long usuarioId;
    private String nombrePaciente;
    private String numeroExpediente;
    private String diagnostico;
    private String tratamiento;
    private String observacionesHtml;
    private LocalDateTime fechaRegistro;
    private Boolean confidencial;

    public ExpedienteDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
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

    public String getObservacionesHtml() {
        return observacionesHtml;
    }

    public void setObservacionesHtml(String observacionesHtml) {
        this.observacionesHtml = observacionesHtml;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getConfidencial() {
        return confidencial;
    }

    public void setConfidencial(Boolean confidencial) {
        this.confidencial = confidencial;
    }
}

