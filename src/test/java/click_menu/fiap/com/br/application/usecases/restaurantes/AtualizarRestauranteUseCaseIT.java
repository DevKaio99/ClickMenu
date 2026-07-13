package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AtualizarRestauranteUseCaseIT {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private RestauranteMapper restauranteMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AtualizarRestauranteUsecase atualizarRestauranteUsecase;

    @Test
    void deveAtualizarRestaurante() {

        Usuario usuarioDonoRestaurante = usuarioRepository.salvarUsuario(new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE));

        Restaurante restaurante = restauranteRepository.salvarRestaurante(new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante));

        UUID id = restaurante.getId();

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "NovoRestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO);

        Restaurante restauranteAtualizado = restauranteRepository.buscarRestaurantePorId(id).orElseThrow();

        assertEquals("NovoRestauranteTeste", restauranteAtualizado.getNomeRestaurante());

    }

    @Test
    void naoDeveAtualizarRestauranteQuandoIdNaoEncontrado () {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = usuarioRepository.salvarUsuario(new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE));

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "NovoRestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        assertThrows(ResourceNotFoundException.class,
                () -> atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO));
    }
}
