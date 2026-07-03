package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO usuarioResponseDTO (Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataUltimaAlteracao(),
                usuario.getTipo()
        );
    }


    public Usuario toEntity (UsuarioCreateDTO usuarioCreateDTO)  {
        return new Usuario(
                usuarioCreateDTO.nome(),
                usuarioCreateDTO.email(),
                usuarioCreateDTO.senha(),
                usuarioCreateDTO.dataUltimaAlteracao(),
                usuarioCreateDTO.tipo());

    }

}

