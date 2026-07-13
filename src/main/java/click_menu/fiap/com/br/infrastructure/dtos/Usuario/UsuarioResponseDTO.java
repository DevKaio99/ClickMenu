package click_menu.fiap.com.br.infrastructure.dtos.Usuario;

import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        LocalDateTime dataUltimaAlteracao,
        TipoUsuarioResponseDTO tipo
) {
}
