package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioMapper {
    public TipoUsuario toEntity(TipoUsuarioCreateDTO tipoUsuarioCreateDTO) {
        return new TipoUsuario(tipoUsuarioCreateDTO.nomeTipo());
    }

    public TipoUsuarioResponseDTO tipoUsuarioResponseDTO(TipoUsuario tipoUsuario) {
        return new TipoUsuarioResponseDTO(
                tipoUsuario.getId(),
                tipoUsuario.getNomeTipo()
        );
    }
}
