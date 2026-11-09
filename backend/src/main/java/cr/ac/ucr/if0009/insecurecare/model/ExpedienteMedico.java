package cr.ac.ucr.if0009.insecurecare.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EXPEDIENTES_MEDICOS")
public class ExpedienteMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "numero_expediente", nullable = false, unique = true, length = 20)
    private String numeroExpediente;

    @Column(nullable = false, length = 200)
    private String diagnostico;

    @Column(nullable = false, length = 500)
    private String tratamiento;

    // [VULNERABILIDAD INTENCIONADA: A03:2021 - Injection (XSS Almacenado)]
    // Campo que almacena codigo HTML y Javascript sin saneamiento de etiquetas
    @Lob
    @Column(name = "observaciones_html")
    private String observacionesHtml;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private Boolean confidencial;

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
        if (confidencial == null) {
            confidencial = true;
        }
    }

    public ExpedienteMedico() {
    }

    public ExpedienteMedico(Usuario usuario, String numeroExpediente, String diagnostico, 
                            String tratamiento, String observacionesHtml, Boolean confidencial) {
        this.usuario = usuario;
        this.numeroExpediente = numeroExpediente;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observacionesHtml = observacionesHtml;
        this.confidencial = confidencial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

