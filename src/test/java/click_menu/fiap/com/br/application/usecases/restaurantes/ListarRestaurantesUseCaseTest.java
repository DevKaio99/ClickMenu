package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.RestauranteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarRestaurantesUseCaseTest {

    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private RestauranteMapper restauranteMapper;

    private ListarRestaurantesUseCase listarRestaurantesUseCase;

    @BeforeEach
    void setUp() {
        listarRestaurantesUseCase = new ListarRestaurantesUseCase(restauranteRepository, restauranteMapper);
    }

    private Restaurante criarRestaurante(String nome) {
        TipoUsuario tipoDono = new TipoUsuario("DONO_RESTAURANTE");
        Usuario dono = new Usuario("Dono", "dono@email.com", "123456", LocalDateTime.now(), tipoDono);

        return new Restaurante(
                nome,
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA),
                dono);
    }

    @Test
    void deveListarTodosOsRestaurantesCadastrados() {
        Restaurante restauranteA = criarRestaurante("RestauranteA");
        Restaurante restauranteB = criarRestaurante("RestauranteB");

        RestauranteResponseDTO responseA = new RestauranteResponseDTO(
                restauranteA.getId(), "RestauranteA", "Rua de exemplo, 344", TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0), LocalTime.of(22, 0), EnumSet.of(DiasDaSemana.SEGUNDA));
        RestauranteResponseDTO responseB = new RestauranteResponseDTO(
                restauranteB.getId(), "RestauranteB", "Rua de exemplo, 344", TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0), LocalTime.of(22, 0), EnumSet.of(DiasDaSemana.SEGUNDA));

        when(restauranteRepository.listarRestaurantes()).thenReturn(List.of(restauranteA, restauranteB));
        when(restauranteMapper.restauranteResponseDTO(restauranteA)).thenReturn(responseA);
        when(restauranteMapper.restauranteResponseDTO(restauranteB)).thenReturn(responseB);

        List<RestauranteResponseDTO> resultado = listarRestaurantesUseCase.executar();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(responseA));
        assertTrue(resultado.contains(responseB));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverRestaurantesCadastrados() {
        when(restauranteRepository.listarRestaurantes()).thenReturn(List.of());

        List<RestauranteResponseDTO> resultado = listarRestaurantesUseCase.executar();

        assertTrue(resultado.isEmpty());
    }
}
