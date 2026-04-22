package com.fiap.ClickMenu.Dtos;

import com.fiap.ClickMenu.Entities.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UsuarioCreateDTO(
        @NotBlank(message = "Campo não pode estar em branco.")
        String nome,
        @NotBlank(message = "Campo não pode estar em branco.")
        @Email
        String email,
        @NotBlank(message = "Campo não pode estar em branco.")
        String login,
        @NotBlank(message = "Campo não pode estar em branco.")
        String senha,
        String endereco,
        LocalDateTime dataUltimaAlteracao,
        @NotNull(message = "Tipo não pode ser nulo.")
        TipoUsuario tipo) {
}
