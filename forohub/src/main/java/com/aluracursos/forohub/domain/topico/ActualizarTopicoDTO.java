package com.aluracursos.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarTopicoDTO(
        @NotNull(message = "Debes ingresar el ID del tópico.")
        Long id,
        @NotBlank(message = "Debes ingresar un título para el tópico.")
        String titulo,
        @NotBlank(message = "Debes ingresar un mensaje detallando tu duda.")
        String mensaje,
        @NotBlank(message = "Debes ingresar el curso sobre el cual se realiza la consulta.")
        String curso
) {
}
