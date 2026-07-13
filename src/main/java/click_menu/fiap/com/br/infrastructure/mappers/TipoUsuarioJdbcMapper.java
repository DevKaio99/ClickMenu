package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class TipoUsuarioJdbcMapper implements RowMapper<TipoUsuario> {

    @Override
    public TipoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapTipoUsuario(rs);
    }

    public TipoUsuario mapTipoUsuario(ResultSet rs) throws SQLException {
        TipoUsuario tipoUsuario = new TipoUsuario(rs.getString("nome_tipo"));
        tipoUsuario.setId(rs.getObject("tipo_usuario_id", UUID.class));

        return tipoUsuario;
    }
}
