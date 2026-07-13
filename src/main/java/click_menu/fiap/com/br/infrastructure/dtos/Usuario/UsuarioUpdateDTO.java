package click_menu.fiap.com.br.infrastructure.dtos.Usuario;

import java.util.UUID;

public record UsuarioUpdateDTO (
        String nome,
        String email,
        UUID tipoId
){
}
