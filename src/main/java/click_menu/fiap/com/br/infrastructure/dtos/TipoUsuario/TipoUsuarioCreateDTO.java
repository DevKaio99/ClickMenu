package click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario;

import jakarta.validation.constraints.NotBlank;

public record TipoUsuarioCreateDTO(
        @NotBlank(message = "O nome do tipo não pode estar em branco")
        String nomeTipo) {
}
