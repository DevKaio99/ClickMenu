package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuscarRestaurantePorIdUseCaseTest {

    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private RestauranteMapper restauranteMapper;

    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @BeforeEach
    void setUp() {
        buscarRestaurantePorIdUseCase = new BuscarRestaurantePorIdUseCase(restauranteRepository, restauranteMapper);
    }

    @Test
    void deveBuscarRestauranteQuandoIdExistente() {
        UUID id = UUID.randomUUID();
        TipoUsuario tipoDono = new TipoUsuario("DONO_RESTAURANTE");
        Usuario dono = new Usuario("Dono", "dono@email.com", "123456", LocalDateTime.now(), tipoDono);

        Restaurante restaurante = new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA),
                dono);

        RestauranteResponseDTO restauranteResponseDTO = new RestauranteResponseDTO(
                id, "RestauranteTeste", "Rua de exemplo, 344", TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0), LocalTime.of(22, 0), EnumSet.of(DiasDaSemana.SEGUNDA));

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));
        when(restauranteMapper.restauranteResponseDTO(restaurante)).thenReturn(restauranteResponseDTO);

        RestauranteResponseDTO resultado = buscarRestaurantePorIdUseCase.executar(id);

        assertNotNull(resultado);
        assertEquals("RestauranteTeste", resultado.nomeRestaurante());
    }

    @Test
    void naoDeveBuscarRestauranteQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> buscarRestaurantePorIdUseCase.executar(id));
    }
}
