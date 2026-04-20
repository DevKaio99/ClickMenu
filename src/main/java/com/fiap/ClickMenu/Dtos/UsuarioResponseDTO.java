package com.fiap.ClickMenu.Dtos;

import com.fiap.ClickMenu.Entities.TipoUsuario;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String login,
        String endereco,
        LocalDateTime dataUltimaAlteracao,
        TipoUsuario tipo
) {
}
