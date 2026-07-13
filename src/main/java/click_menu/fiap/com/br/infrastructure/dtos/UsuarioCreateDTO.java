package click_menu.fiap.com.br.infrastructure.dtos;

import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UsuarioCreateDTO(
        @NotBlank(message = "O campo não pode estar enm branco")
        String nome,
        @Email(message = "O Email inserido é inválido")
        String email,
        @NotBlank(message = "Insira uma senha")
        String senha,
        LocalDateTime dataUltimaAlteracao,
        @NotNull
        TipoUsuario tipo
) {
}
