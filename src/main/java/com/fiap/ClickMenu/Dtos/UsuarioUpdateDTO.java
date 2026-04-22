package com.fiap.ClickMenu.Dtos;

import com.fiap.ClickMenu.Entities.TipoUsuario;

public record UsuarioUpdateDTO(
        String nome,
        String email,
        String login,
        String endereco,
        TipoUsuario tipo) {
}
