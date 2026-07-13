package click_menu.fiap.com.br.infrastructure.dtos.Usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdatePassDTO(
        String senhaAtual,
        @NotBlank(message = "Insira uma nova senha válida")
        String senhaNova) {
}
