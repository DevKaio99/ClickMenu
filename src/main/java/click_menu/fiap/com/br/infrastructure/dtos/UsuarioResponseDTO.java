package click_menu.fiap.com.br.infrastructure.dtos;

import click_menu.fiap.com.br.domain.enums.TipoUsuario;


import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        LocalDateTime dataUltimaAlteracao,
        TipoUsuario tipo
) {
}
