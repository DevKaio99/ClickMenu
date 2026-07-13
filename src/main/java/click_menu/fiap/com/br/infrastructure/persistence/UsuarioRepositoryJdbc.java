package click_menu.fiap.com.br.infrastructure.persistence;

import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryJdbc implements UsuarioRepository {
    private final JdbcTemplate jdbc;
    private final UsuarioJdbcMapper usuarioJdbcMapper;


    public UsuarioRepositoryJdbc(JdbcTemplate jdbc, UsuarioJdbcMapper usuarioJdbcMapper) {
        this.jdbc = jdbc;
        this.usuarioJdbcMapper = usuarioJdbcMapper;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {

        String sql = """
                INSERT INTO usuario (id, nome, email, senha, data_ultima_alteracao, tipo_id) VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbc.update(sql,
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getDataUltimaAlteracao(),
                    usuario.getTipo().getId()
        );

        return usuario;
    }


    @Override
    public void deletarUsuario(UUID id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        jdbc.update(sql, id);
    }

    @Override
    public boolean validarEmailExistente(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        Integer total = jdbc.queryForObject(sql, Integer.class, email);
        return total !=null && total > 0;
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(UUID id) {

        String sql = """
            SELECT
                u.id AS usuario_id,
                u.nome,
                u.email,
                u.senha,
                u.data_ultima_alteracao,

                t.id AS tipo_usuario_id,
                t.nome_tipo
            FROM usuario u
            JOIN tipo_usuario t ON t.id = u.tipo_id
            WHERE u.id = ?
            """;

        return jdbc.query(sql, usuarioJdbcMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, data_ultima_alteracao = ? WHERE id = ?";
        jdbc.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getDataUltimaAlteracao(), usuario.getId());
        return usuario;
    }

    @Override
    public Optional<Usuario> findByEmailIgnoreCase(String email) {
        String sql = """
    SELECT
        u.id AS usuario_id,
        u.nome,
        u.email,
        u.senha,
        u.data_ultima_alteracao,

        t.id AS tipo_usuario_id,
        t.nome_tipo
    FROM usuario u
    JOIN tipo_usuario t ON t.id = u.tipo_id
    WHERE LOWER(u.email) = LOWER(?)
    """;

        List<Usuario> usuarios = jdbc.query(
                sql,
                usuarioJdbcMapper,
                email
        );

        return usuarios.stream().findFirst();
    }

    @Override
    public Usuario atualizarSenha(Usuario usuario) {

        String sql = """
        UPDATE usuario
        SET senha = ?,
            data_ultima_alteracao = ?
        WHERE id = ?
        """;

        jdbc.update(
                sql,
                usuario.getSenha(),
                usuario.getDataUltimaAlteracao(),
                usuario.getId()
        );

        return usuario;
    }

}
