package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UsuarioJdbcMapper implements RowMapper<Usuario> {

    private final TipoUsuarioJdbcMapper tipoUsuarioJdbcMapper;

    public UsuarioJdbcMapper(TipoUsuarioJdbcMapper tipoUsuarioJdbcMapper) {
        this.tipoUsuarioJdbcMapper = tipoUsuarioJdbcMapper;
    }

    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapUsuario(rs);
    }

    public Usuario mapUsuario(ResultSet rs) throws SQLException {
        TipoUsuario tipo = tipoUsuarioJdbcMapper.mapTipoUsuario(rs);

        Usuario usuario = new Usuario(
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getObject("data_ultima_alteracao", LocalDateTime.class),
                tipo
        );
        usuario.setId(rs.getObject("usuario_id", UUID.class));

        return usuario;
    }
}
