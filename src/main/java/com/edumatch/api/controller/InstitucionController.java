package com.edumatch.api.controller;

import com.edumatch.api.exception.RecursoNoEncontradoException;
import com.edumatch.api.model.Institucion;
import com.edumatch.api.repository.InstitucionRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** CRUD de instituciones (universidades y entidades que ofrecen oportunidades). */
@RestController
@RequestMapping("/api/instituciones")
public class InstitucionController {

    private final InstitucionRepository institucionRepository;

    public InstitucionController(InstitucionRepository institucionRepository) {
        this.institucionRepository = institucionRepository;
    }

    /** GET /api/instituciones -> lista todas. */
    @GetMapping
    public List<Institucion> listar() {
        return institucionRepository.findAll();
    }

    /** GET /api/instituciones/{id} -> consulta una. */
    @GetMapping("/{id}")
    public Institucion consultar(@PathVariable Long id) {
        return buscar(id);
    }

    /** POST /api/instituciones -> inserta una (201). */
    @PostMapping
    public ResponseEntity<Institucion> crear(@Valid @RequestBody Institucion institucion) {
        institucion.setId(null); // fuerza que sea un registro nuevo
        return ResponseEntity.status(HttpStatus.CREATED).body(institucionRepository.save(institucion));
    }

    /** PUT /api/instituciones/{id} -> actualiza. */
    @PutMapping("/{id}")
    public Institucion actualizar(@PathVariable Long id, @Valid @RequestBody Institucion datos) {
        Institucion institucion = buscar(id);
        institucion.setNombre(datos.getNombre());
        institucion.setCiudad(datos.getCiudad());
        institucion.setPais(datos.getPais());
        institucion.setSitioWeb(datos.getSitioWeb());
        return institucionRepository.save(institucion);
    }

    /** DELETE /api/instituciones/{id} -> elimina (204). */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        institucionRepository.delete(buscar(id));
        return ResponseEntity.noContent().build();
    }

    private Institucion buscar(Long id) {
        return institucionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Institución no encontrada: " + id));
    }
}
