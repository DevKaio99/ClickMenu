package click_menu.fiap.com.br.infrastructure.persistence;

import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class RestauranteRepositoryJdbc implements RestauranteRepository {
    private final JdbcTemplate jdbc;

    public RestauranteRepositoryJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean validarNomeEEmailExistente(String nomeRestaurante, String enderecoRestaurante) {
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
                restaurante.getDiasFuncionamento().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(",")),
                restaurante.getDonoRestaurante().getId()
        );

        return restaurante;
    }
}
