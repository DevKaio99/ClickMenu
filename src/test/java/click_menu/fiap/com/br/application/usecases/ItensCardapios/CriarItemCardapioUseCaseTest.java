package click_menu.fiap.com.br.application.usecases.ItensCardapios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.io.Resource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;
    @Mock
    private ItemCardapioMapper itemCardapioMapper;
    @Mock
    private RestauranteRepository restauranteRepository;

    private CriarItemCardapioUseCase criarItemCardapioUseCase;


    @BeforeEach
    void setUp() {
        criarItemCardapioUseCase = new CriarItemCardapioUseCase(itemCardapioRepository, itemCardapioMapper, restauranteRepository);
    }

    @Test
    void deveCriarItemCardapio () {
        UUID id = UUID.randomUUID();
            Usuario usuarioTeste = new Usuario(
                    "Teste","teste@email.com",
                    "123456",
                    LocalDateTime.now(),
                    TipoUsuario.DONO_RESTAURANTE);

            Restaurante restaurante = new Restaurante(
                    "RestauranteTeste",
                    "Rua de exemplo, 344",
                    TipoCozinhaRestaurante.JAPONESA,
                    LocalTime.now(),
                    LocalTime.now(),
                    EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                    usuarioTeste);

            ItemCardapio itemCardapio = new ItemCardapio(
                            "Frango a milanesa",
                            "frango empanado com farinha",
                            BigDecimal.valueOf(29.90),
                            true,
                            "/...",
                            restaurante);

            ItemCardapioCreateDTO itemCardapioCreateDTO = new ItemCardapioCreateDTO(
                    "Frango a milanesa",
                    "frango empanado com farinha",
                    BigDecimal.valueOf(29.90),
                    true,
                    "/...",
                    id);

             ItemCardapioResponseDTO itemCardapioResponseDTO = new ItemCardapioResponseDTO(
                     id,
                    "Frango a milanesa",
                    "frango empanado com farinha",
                    BigDecimal.valueOf(29.90),
                    true,
                    "/..."
                    );

            when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));
            when(itemCardapioMapper.toEntity(itemCardapioCreateDTO, restaurante)).thenReturn(itemCardapio);
            when(itemCardapioRepository.salvarItemCardapio(itemCardapio)).thenReturn(itemCardapio);
            when(itemCardapioMapper.itemCardapioResponseDTO(itemCardapio)).thenReturn(itemCardapioResponseDTO);

            ItemCardapioResponseDTO resultado = criarItemCardapioUseCase.executar(itemCardapioCreateDTO);

            assertNotNull(resultado);
            assertEquals("Frango a milanesa", itemCardapio.getNome());

            verify(itemCardapioRepository).salvarItemCardapio(itemCardapio);

        }

    @Test
    void naoDeveCriarItemCardapioQuandoIdRestauranteNaoEncontrado() {
        UUID id = UUID.randomUUID();

        ItemCardapioCreateDTO itemCardapioCreateDTO = new ItemCardapioCreateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                id);

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> criarItemCardapioUseCase.executar(itemCardapioCreateDTO));

        verify(itemCardapioRepository, never()).salvarItemCardapio(any());
    }

    }

