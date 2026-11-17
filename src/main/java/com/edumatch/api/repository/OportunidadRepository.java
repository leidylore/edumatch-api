package com.edumatch.api.repository;

import com.edumatch.api.model.Oportunidad;
import com.edumatch.api.model.TipoOportunidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OportunidadRepository extends JpaRepository<Oportunidad, Long> {

    /** Filtra las oportunidades por tipo (BECA, CURSO o PROGRAMA_INTERCAMBIO). */
    List<Oportunidad> findByTipo(TipoOportunidad tipo);
}
