package com.aluracursos.forohub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTituloIgnoreCase(String titulo);

    Boolean existsByMensajeIgnoreCase(String mensaje);

}
