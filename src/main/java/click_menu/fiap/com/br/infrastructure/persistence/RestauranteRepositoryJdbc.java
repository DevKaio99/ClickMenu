package click_menu.fiap.com.br.infrastructure.persistence;

import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteJdbcMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RestauranteRepositoryJdbc implements RestauranteRepository {
    private final JdbcTemplate jdbc;
    private final RestauranteJdbcMapper restauranteJdbcMapper;

    public RestauranteRepositoryJdbc(JdbcTemplate jdbc, RestauranteJdbcMapper restauranteJdbcMapper) {
        this.jdbc = jdbc;
        this.restauranteJdbcMapper = restauranteJdbcMapper;
    }

    @Override
    public boolean validarNomeEEnderecoExistente(String nomeRestaurante, String enderecoRestaurante) {
        String sql = "SELECT COUNT(*) FROM restaurante WHERE nome_restaurante = ? AND endereco_restaurante = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, nomeRestaurante, enderecoRestaurante);
        return count != null && count > 0;
    }

    @Override
    public Restaurante salvarRestaurante(Restaurante restaurante) {
        String sql = """
                INSERT INTO restaurante (id, nome_restaurante, endereco_restaurante, tipo_cozinha, horario_abertura, horario_fechamento, dias_funcionamento, dono_restaurante) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbc.update(sql,
                restaurante.getId(),
                restaurante.getNomeRestaurante(),
                restaurante.getEnderecoRestaurante(),
                restaurante.getTipoCozinha().name(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                converterDiasFuncionamento(restaurante.getDiasFuncionamento()),
                restaurante.getDonoRestaurante().getId()
        );

        return restaurante;
    }

    @Override
    public Optional<Restaurante> buscarRestaurantePorId(UUID id) {
        String sql = """
            SELECT
                r.id AS restaurante_id,
                r.nome_restaurante,
                r.endereco_restaurante,
                r.tipo_cozinha,
                r.horario_abertura,
                r.horario_fechamento,
                r.dias_funcionamento,

                u.id AS usuario_id,
                u.nome,
                u.email,
                u.senha,
                u.data_ultima_alteracao,
                u.tipo

        FROM restaurante r
        INNER JOIN usuario u
            ON u.id = r.dono_restaurante

        WHERE r.id = ?
        """;
        return jdbc.query(sql, restauranteJdbcMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public Restaurante atualizarRestaurante(Restaurante restaurante) {

        String sql = """
            UPDATE restaurante
            SET nome_restaurante = ?,
                endereco_restaurante = ?,
                tipo_cozinha = ?,
                horario_abertura = ?,
                horario_fechamento = ?,
                dias_funcionamento = ?,
                dono_restaurante = ?
            WHERE id = ?
            """;

        jdbc.update(
                sql,
                restaurante.getNomeRestaurante(),
                restaurante.getEnderecoRestaurante(),
                restaurante.getTipoCozinha().name(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                converterDiasFuncionamento(restaurante.getDiasFuncionamento()),
                restaurante.getDonoRestaurante().getId(),
                restaurante.getId()
        );

        return restaurante;
    }

    @Override
    public void deletarRestaurante(UUID id) {
        String sql = "DELETE FROM restaurante WHERE id = ?";
        jdbc.update(sql, id);
    }


    private String converterDiasFuncionamento(Set<DiasDaSemana> dias) {
        return dias.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }
}
