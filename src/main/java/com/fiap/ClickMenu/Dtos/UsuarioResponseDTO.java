package com.fiap.ClickMenu.Dtos;

import com.fiap.ClickMenu.Entities.TipoUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        String endereco,
        TipoUsuario tipo
) {
}
