package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestauranteJdbcMapper implements RowMapper <Restaurante> {

    private final UsuarioJdbcMapper usuarioJdbcMapper;

    public RestauranteJdbcMapper(UsuarioJdbcMapper usuarioJdbcMapper) {
        this.usuarioJdbcMapper = usuarioJdbcMapper;
    }

    @Override
    public Restaurante mapRow(ResultSet rs, int rowNum) throws SQLException {

        Set<DiasDaSemana> diasFuncionamento = Arrays.stream(
                        rs.getString("dias_funcionamento").split(","))
                .map(DiasDaSemana::valueOf)
                .collect(Collectors.toSet());

        Usuario dono = usuarioJdbcMapper.mapUsuario(rs);

        Restaurante restaurante = new Restaurante(
                rs.getString("nome_restaurante"),
                rs.getString("endereco_restaurante"),
                TipoCozinhaRestaurante.valueOf(rs.getString("tipo_cozinha")),
                rs.getObject("horario_abertura", LocalTime.class),
                rs.getObject("horario_fechamento", LocalTime.class),
                diasFuncionamento,
                dono
        );

        restaurante.setId(rs.getObject("restaurante_id", UUID.class));

        return restaurante;
    }
}
