package com.fiap.ClickMenu.Dtos;

import com.fiap.ClickMenu.Entities.TipoUsuario;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank
        String nome,
        String email,
        @NotBlank
        String login,
        @NotBlank
        String senha,
        String endereco,
        @NotBlank
        TipoUsuario tipo) {
}
