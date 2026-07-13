package click_menu.fiap.com.br.infrastructure.persistence;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.mappers.TipoUsuarioJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TipoUsuarioRepositoryJdbc implements TipoUsuarioRepository {
    private final JdbcTemplate jdbc;
    private final TipoUsuarioJdbcMapper tipoUsuarioJdbcMapper;

    public TipoUsuarioRepositoryJdbc(JdbcTemplate jdbc, TipoUsuarioJdbcMapper tipoUsuarioJdbcMapper) {
        this.jdbc = jdbc;
        this.tipoUsuarioJdbcMapper = tipoUsuarioJdbcMapper;
    }

    @Override
    public boolean validarNomeTipoExistente(String nomeTipo) {
        String sql = "SELECT COUNT(*) FROM tipo_usuario WHERE nome_tipo = ?";
        Integer total = jdbc.queryForObject(sql, Integer.class, nomeTipo);
        return total != null && total > 0;
    }

    @Override
    public TipoUsuario salvarTipoUsuario(TipoUsuario tipoUsuario) {
        String sql = """
                INSERT INTO tipo_usuario (id, nome_tipo) VALUES (?, ?)
                """;

        jdbc.update(sql, tipoUsuario.getId(), tipoUsuario.getNomeTipo());

        return tipoUsuario;
    }

    @Override
    public List<TipoUsuario> listarTiposUsuario() {
        String sql = """
                SELECT
                    id AS tipo_usuario_id,
                    nome_tipo
                FROM tipo_usuario
                """;

        return jdbc.query(sql, tipoUsuarioJdbcMapper);
    }

    @Override
    public Optional<TipoUsuario> buscarTipoUsuarioPorId(UUID id) {
        String sql = """
                SELECT
                    id AS tipo_usuario_id,
                    nome_tipo
                FROM tipo_usuario
                WHERE id = ?
                """;

        return jdbc.query(sql, tipoUsuarioJdbcMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public TipoUsuario atualizarTipoUsuario(TipoUsuario tipoUsuario) {
        String sql = "UPDATE tipo_usuario SET nome_tipo = ? WHERE id = ?";
        jdbc.update(sql, tipoUsuario.getNomeTipo(), tipoUsuario.getId());

        return tipoUsuario;
    }

    @Override
    public void deletarTipoUsuario(UUID id) {
        String sql = "DELETE FROM tipo_usuario WHERE id = ?";
        jdbc.update(sql, id);
    }
}
