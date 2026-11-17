package com.edumatch.api.repository;

import com.edumatch.api.model.Postulacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    List<Postulacion> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndOportunidadId(Long usuarioId, Long oportunidadId);
}
