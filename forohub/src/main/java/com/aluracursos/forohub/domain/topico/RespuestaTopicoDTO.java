package com.aluracursos.forohub.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RespuestaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaCreacion,
        Status status,
        Long id_usuario,
        String curso
) {
    public RespuestaTopicoDTO(Topico topico) {
        this(topico.getId(),topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),topico.getStatus(), topico.getAutor().getId(), topico.getCurso());
    }
}
