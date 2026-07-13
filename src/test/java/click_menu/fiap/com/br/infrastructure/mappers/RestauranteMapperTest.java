package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteMapperTest {

    private final RestauranteMapper restauranteMapper = new RestauranteMapper();

    @Test
    void deveConverterCreateDTOParaEntidade() {
        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        RestauranteCreateDTO restauranteCreateDTO = new RestauranteCreateDTO(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        Restaurante restaurante = restauranteMapper.toEntity(restauranteCreateDTO, usuarioDonoRestaurante);

        assertNotNull(restaurante.getId());
        assertEquals("RestauranteTeste", restaurante.getNomeRestaurante());
        assertEquals(usuarioDonoRestaurante, restaurante.getDonoRestaurante());
    }

    @Test
    void deveConverterEntidadeParaResponseDTO() {
        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        Restaurante restaurante = new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante);

        RestauranteResponseDTO resultado = restauranteMapper.restauranteResponseDTO(restaurante);

        assertEquals(restaurante.getId(), resultado.id());
        assertEquals("RestauranteTeste", resultado.nomeRestaurante());
        assertEquals(TipoCozinhaRestaurante.JAPONESA, resultado.tipoCozinha());
    }
}
