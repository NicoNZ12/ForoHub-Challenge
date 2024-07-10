package com.aluracursos.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ActualizarUsuarioDTO(
        @NotNull(message = "Debes ingresar el ID del usuario.")
        Long id,
        @NotBlank(message = "El nombre es obligatorio.")
        String nombre,
        @NotBlank(message = "Debes ingresar un correo válido para registrate.")
        @Email(message = "El formato del correo no es válido.")
        String email,
        @NotBlank(message = "Debes ingresar una contraseña.")
        @Pattern(regexp = "^[\\w\\W]{6,9}$", message = "La contraseña tiene que tener entre 6 y 9 caractres.")
        String password
) {
}
