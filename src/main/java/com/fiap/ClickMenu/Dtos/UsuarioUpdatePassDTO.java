package com.fiap.ClickMenu.Dtos;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdatePassDTO(
        String senhaAtual,
        @NotBlank(message = "Insira uma nova senha válida")
        String senhaNova) {
}
