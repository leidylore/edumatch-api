package com.edumatch.api.controller;

import com.edumatch.api.dto.OportunidadRequest;
import com.edumatch.api.exception.RecursoNoEncontradoException;
import com.edumatch.api.model.Institucion;
import com.edumatch.api.model.Oportunidad;
import com.edumatch.api.model.TipoOportunidad;
import com.edumatch.api.repository.InstitucionRepository;
import com.edumatch.api.repository.OportunidadRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** CRUD de oportunidades: becas, cursos y programas de intercambio. */
@RestController
@RequestMapping("/api/oportunidades")
public class OportunidadController {

    private final OportunidadRepository oportunidadRepository;
    private final InstitucionRepository institucionRepository;

    public OportunidadController(OportunidadRepository oportunidadRepository,
                                 InstitucionRepository institucionRepository) {
        this.oportunidadRepository = oportunidadRepository;
        this.institucionRepository = institucionRepository;
    }

    /** GET /api/oportunidades?tipo=BECA -> lista todas o filtra por tipo. */
    @GetMapping
    public List<Oportunidad> listar(@RequestParam(required = false) TipoOportunidad tipo) {
        return (tipo == null) ? oportunidadRepository.findAll() : oportunidadRepository.findByTipo(tipo);
    }

    /** GET /api/oportunidades/{id} -> consulta una. */
    @GetMapping("/{id}")
    public Oportunidad consultar(@PathVariable Long id) {
        return buscar(id);
    }

    /** POST /api/oportunidades -> inserta una (201). */
    @PostMapping
    public ResponseEntity<Oportunidad> crear(@Valid @RequestBody OportunidadRequest datos) {
        Oportunidad oportunidad = new Oportunidad();
        copiarDatos(oportunidad, datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(oportunidadRepository.save(oportunidad));
    }

    /** PUT /api/oportunidades/{id} -> actualiza. */
    @PutMapping("/{id}")
    public Oportunidad actualizar(@PathVariable Long id, @Valid @RequestBody OportunidadRequest datos) {
        Oportunidad oportunidad = buscar(id);
        copiarDatos(oportunidad, datos);
        return oportunidadRepository.save(oportunidad);
    }

    /** DELETE /api/oportunidades/{id} -> elimina (204). */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        oportunidadRepository.delete(buscar(id));
        return ResponseEntity.noContent().build();
    }

    private Oportunidad buscar(Long id) {
        return oportunidadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Oportunidad no encontrada: " + id));
    }

    /** Pasa los datos recibidos a la entidad y valida que la institución exista. */
    private void copiarDatos(Oportunidad oportunidad, OportunidadRequest datos) {
        Institucion institucion = institucionRepository.findById(datos.institucionId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Institución no encontrada: " + datos.institucionId()));
        oportunidad.setTitulo(datos.titulo());
        oportunidad.setDescripcion(datos.descripcion());
        oportunidad.setTipo(datos.tipo());
        oportunidad.setFechaLimite(datos.fechaLimite());
        oportunidad.setInstitucion(institucion);
    }
}
