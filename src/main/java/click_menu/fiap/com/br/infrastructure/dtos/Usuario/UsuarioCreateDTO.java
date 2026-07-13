package click_menu.fiap.com.br.infrastructure.dtos.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioCreateDTO(
        @NotBlank(message = "O campo não pode estar em branco")
        String nome,
        @Email(message = "O Email inserido é inválido")
        String email,
        @NotBlank(message = "Insira uma senha")
        String senha,
        LocalDateTime dataUltimaAlteracao,
        @NotNull(message = "Informe o tipo de usuário")
        UUID tipoId
) {
}
