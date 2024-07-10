package com.aluracursos.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearTopicoDTO(
        @NotBlank(message = "Debes ingresar un título para el tópico.")
        String titulo,
        @NotBlank(message = "Debes ingresar un mensaje detallando tu duda.")
        String mensaje,
        @NotNull(message = "Debes ingresar el ID del autor.")
        Long id_autor,
        @NotBlank(message = "Debes ingresar el curso sobre el cual se realiza la consulta.")
        String curso
) {
}
