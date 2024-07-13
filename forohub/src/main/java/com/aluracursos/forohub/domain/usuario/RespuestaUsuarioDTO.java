package com.aluracursos.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RespuestaUsuarioDTO(
        Long id,
        String nombre,
        String email,
        Boolean activo
) {
}
