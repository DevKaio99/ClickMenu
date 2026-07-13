package click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario;

import java.util.UUID;

public record TipoUsuarioResponseDTO(
        UUID id,
        String nomeTipo) {
}
