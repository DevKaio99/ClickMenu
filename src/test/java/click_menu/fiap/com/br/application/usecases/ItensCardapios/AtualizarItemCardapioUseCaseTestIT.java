package click_menu.fiap.com.br.application.usecases.ItensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioUpdateDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AtualizarItemCardapioUseCaseTestIT {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;

    @Test
    void deveAtualizarItemCardapio() {

        Usuario usuarioDonoRestaurante = usuarioRepository.salvarUsuario(new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE));

        Restaurante restaurante = restauranteRepository.salvarRestaurante(new Restaurante (
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante
        ));

        ItemCardapio itemCardapio = itemCardapioRepository.salvarItemCardapio(new ItemCardapio(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante));

        UUID id = itemCardapio.getId();

        ItemCardapioUpdateDTO itemCardapioUpdateDTO = new ItemCardapioUpdateDTO(
                "Frango a parmegiana",
                "frango com queijo",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante.getId()
        );

        atualizarItemCardapioUseCase.executar(id, itemCardapioUpdateDTO);

        ItemCardapio itemCardapioAtualizado = itemCardapioRepository.buscarItemCardapioPorId(id).orElseThrow();
        assertEquals("Frango a parmegiana", itemCardapioAtualizado.getNome());
        assertEquals("frango com queijo", itemCardapioAtualizado.getDescricao());
        assertEquals(restaurante.getId(), itemCardapioAtualizado.getRestaurante().getId());
    }

    @Test
    void naoDevePermitirAtualizarQuandoRestauranteInexistente() {
        Usuario usuarioDonoRestaurante = usuarioRepository.salvarUsuario(new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE));

        Restaurante restaurante = restauranteRepository.salvarRestaurante(new Restaurante (
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante
        ));

        ItemCardapio itemCardapio = itemCardapioRepository.salvarItemCardapio(new ItemCardapio(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante));

        UUID id = itemCardapio.getId();
        UUID idRandom = UUID.randomUUID();

        ItemCardapioUpdateDTO itemCardapioUpdateDTO = new ItemCardapioUpdateDTO(
                "Frango a parmegiana",
                "frango com queijo",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                idRandom

        );

        assertThrows(ResourceNotFoundException.class,
                () -> atualizarItemCardapioUseCase.executar(id, itemCardapioUpdateDTO));

    }

}
