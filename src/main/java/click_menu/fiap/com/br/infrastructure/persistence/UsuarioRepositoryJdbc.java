package click_menu.fiap.com.br.infrastructure.persistence;

import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryJdbc implements UsuarioRepository {
    private final JdbcTemplate jdbc;


    public UsuarioRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {

        String sql = """
                INSERT INTO usuario (id, nome, email, senha, data_ultima_alteracao, tipo) VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbc.update(sql,
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getDataUltimaAlteracao(),
                    usuario.getTipo().name()
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
    String sql = "SELECT * FROM usuario WHERE id = ?";
    return jdbc.query(sql, this::mapearUsuario, id).stream().findFirst();
    }

    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, data_ultima_alteracao = ? WHERE id = ?";
        jdbc.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getDataUltimaAlteracao(), usuario.getId());
        return usuario;
    }

    private Usuario mapearUsuario(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getObject("data_ultima_alteracao", LocalDateTime.class),
                TipoUsuario.valueOf(rs.getString("tipo"))
        );
        usuario.setId(rs.getObject("id", UUID.class));
        return usuario;
    }
}
