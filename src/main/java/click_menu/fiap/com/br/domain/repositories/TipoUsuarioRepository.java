package click_menu.fiap.com.br.domain.repositories;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoUsuarioRepository {
    boolean validarNomeTipoExistente(String nomeTipo);
    TipoUsuario salvarTipoUsuario(TipoUsuario tipoUsuario);
    List<TipoUsuario> listarTiposUsuario();
    Optional<TipoUsuario> buscarTipoUsuarioPorId(UUID id);
    TipoUsuario atualizarTipoUsuario(TipoUsuario tipoUsuario);
    void deletarTipoUsuario(UUID id);
}
