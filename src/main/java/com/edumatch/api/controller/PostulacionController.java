package com.edumatch.api.controller;

import com.edumatch.api.dto.EstadoRequest;
import com.edumatch.api.dto.PostulacionRequest;
import com.edumatch.api.exception.ConflictoException;
import com.edumatch.api.exception.RecursoNoEncontradoException;
import com.edumatch.api.model.Oportunidad;
import com.edumatch.api.model.Postulacion;
import com.edumatch.api.model.Usuario;
import com.edumatch.api.repository.OportunidadRepository;
import com.edumatch.api.repository.PostulacionRepository;
import com.edumatch.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Servicios de postulaciones: un estudiante se postula a una oportunidad. */
@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionController {

    private final PostulacionRepository postulacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final OportunidadRepository oportunidadRepository;

    public PostulacionController(PostulacionRepository postulacionRepository,
                                 UsuarioRepository usuarioRepository,
                                 OportunidadRepository oportunidadRepository) {
        this.postulacionRepository = postulacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.oportunidadRepository = oportunidadRepository;
    }

    /** POST /api/postulaciones -> crea la postulación (201). Evita postularse dos veces a lo mismo. */
    @PostMapping
    public ResponseEntity<Postulacion> crear(@Valid @RequestBody PostulacionRequest datos) {
        Usuario usuario = usuarioRepository.findById(datos.usuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + datos.usuarioId()));
        Oportunidad oportunidad = oportunidadRepository.findById(datos.oportunidadId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Oportunidad no encontrada: " + datos.oportunidadId()));

        if (postulacionRepository.existsByUsuarioIdAndOportunidadId(usuario.getId(), oportunidad.getId())) {
            throw new ConflictoException("El estudiante ya se postuló a esta oportunidad");
        }

        Postulacion postulacion = new Postulacion();
        postulacion.setUsuario(usuario);
        postulacion.setOportunidad(oportunidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(postulacionRepository.save(postulacion));
    }

    /** GET /api/postulaciones/usuario/{usuarioId} -> postulaciones de un estudiante. */
    @GetMapping("/usuario/{usuarioId}")
    public List<Postulacion> listarPorUsuario(@PathVariable Long usuarioId) {
        return postulacionRepository.findByUsuarioId(usuarioId);
    }

    /** GET /api/postulaciones/{id} -> consulta una postulación. */
    @GetMapping("/{id}")
    public Postulacion consultar(@PathVariable Long id) {
        return buscar(id);
    }

    /** PUT /api/postulaciones/{id}/estado -> cambia el estado. */
    @PutMapping("/{id}/estado")
    public Postulacion cambiarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequest datos) {
        Postulacion postulacion = buscar(id);
        postulacion.setEstado(datos.estado());
        return postulacionRepository.save(postulacion);
    }

    /** DELETE /api/postulaciones/{id} -> cancela la postulación (204). */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        postulacionRepository.delete(buscar(id));
        return ResponseEntity.noContent().build();
    }

    private Postulacion buscar(Long id) {
        return postulacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Postulación no encontrada: " + id));
    }
}
