package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final TipoUsuarioMapper tipoUsuarioMapper;

    public UsuarioMapper(TipoUsuarioMapper tipoUsuarioMapper) {
        this.tipoUsuarioMapper = tipoUsuarioMapper;
    }

    public UsuarioResponseDTO usuarioResponseDTO (Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataUltimaAlteracao(),
                tipoUsuarioMapper.tipoUsuarioResponseDTO(usuario.getTipo())
        );
    }


    public Usuario toEntity (UsuarioCreateDTO usuarioCreateDTO, TipoUsuario tipo)  {
        return new Usuario(
                usuarioCreateDTO.nome(),
                usuarioCreateDTO.email(),
                usuarioCreateDTO.senha(),
                usuarioCreateDTO.dataUltimaAlteracao(),
                tipo);

    }

}

