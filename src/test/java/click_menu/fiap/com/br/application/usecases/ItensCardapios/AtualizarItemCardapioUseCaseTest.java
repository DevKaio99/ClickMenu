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
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioUpdateDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarItemCardapioUseCaseTest {
    @Mock
    private ItemCardapioRepository itemCardapioRepository;
    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private ItemCardapioMapper itemCardapioMapper;

    private AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;

    @BeforeEach
    void setUp() {
        atualizarItemCardapioUseCase = new AtualizarItemCardapioUseCase(itemCardapioRepository, restauranteRepository, itemCardapioMapper);
    }

    @Test
    void deveAtualizarItemCardapio() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE);
        usuarioDonoRestaurante.setId(id);

        Restaurante restaurante = new Restaurante (
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante
        );

        ItemCardapio itemCardapio = new ItemCardapio(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante);

        ItemCardapioResponseDTO itemCardapioResponseDTO = new ItemCardapioResponseDTO(
                id,
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/..."
        );

        ItemCardapioUpdateDTO itemCardapioUpdateDTO = new ItemCardapioUpdateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                id
        );

        when(itemCardapioRepository.buscarItemCardapioPorId(id)).thenReturn(Optional.of(itemCardapio));
        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));
        when(itemCardapioRepository.atualizarItemCardapio(itemCardapio)).thenReturn(itemCardapio);
        when(itemCardapioMapper.itemCardapioResponseDTO(itemCardapio)).thenReturn(itemCardapioResponseDTO);

        atualizarItemCardapioUseCase.executar(id, itemCardapioUpdateDTO);

        verify(itemCardapioRepository).atualizarItemCardapio(itemCardapio);

    }

    @Test
    void naoDevePermitirAtualizaritemCardapioQuandoIdNaoEncontrado() {
        UUID id = UUID.randomUUID();

        ItemCardapioUpdateDTO itemCardapioUpdateDTO = new ItemCardapioUpdateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                id
        );

        when(itemCardapioRepository.buscarItemCardapioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarItemCardapioUseCase.executar(id, itemCardapioUpdateDTO));

        verify(itemCardapioRepository, never()).atualizarItemCardapio(any());

    }

    @Test
    void naoDevePermitirAtualizarItemCardapioQuandoIdRestauranteNaoEncontrado() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE);
        usuarioDonoRestaurante.setId(id);

        Restaurante restaurante = new Restaurante (
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante
        );

        ItemCardapioUpdateDTO itemCardapioUpdateDTO = new ItemCardapioUpdateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                id
        );

        ItemCardapio itemCardapio = new ItemCardapio(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante);

        when(itemCardapioRepository.buscarItemCardapioPorId(id)).thenReturn(Optional.of(itemCardapio));
        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarItemCardapioUseCase.executar(id, itemCardapioUpdateDTO));

        verify(itemCardapioRepository, never()).atualizarItemCardapio(any());

    }
}

