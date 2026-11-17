package com.edumatch.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Postulación de un estudiante a una oportunidad. */
@Entity
@Table(name = "postulacion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "oportunidad_id"}))
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "oportunidad_id")
    private Oportunidad oportunidad;

    @Column(nullable = false)
    private LocalDateTime fechaPostulacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPostulacion estado = EstadoPostulacion.ENVIADA;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Oportunidad getOportunidad() { return oportunidad; }
    public void setOportunidad(Oportunidad oportunidad) { this.oportunidad = oportunidad; }
    public LocalDateTime getFechaPostulacion() { return fechaPostulacion; }
    public void setFechaPostulacion(LocalDateTime fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; }
    public EstadoPostulacion getEstado() { return estado; }
    public void setEstado(EstadoPostulacion estado) { this.estado = estado; }
}
