package click_menu.fiap.com.br.infrastructure.dtos.Usuario;

import click_menu.fiap.com.br.domain.enums.TipoUsuario;

public record UsuarioUpdateDTO (
        String nome,
        String email,
        TipoUsuario tipo
){
}
