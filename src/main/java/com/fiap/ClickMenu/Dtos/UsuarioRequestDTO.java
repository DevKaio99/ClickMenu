package com.fiap.ClickMenu.Dtos;

import com.fiap.ClickMenu.Entities.TipoUsuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UsuarioRequestDTO(
        @NotBlank
        String nome,
        String email,
        @NotBlank
        String login,
        @NotBlank
        String senha,
        String endereco,
        LocalDateTime dataUltimaAlteracao,
        @NotBlank
        TipoUsuario tipo) {
}
