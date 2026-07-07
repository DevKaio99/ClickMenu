package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarRestauranteUseCaseTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    private DeletarRestauranteUseCase deletarRestauranteUseCase;

    @BeforeEach
    void setUp() {
        deletarRestauranteUseCase = new DeletarRestauranteUseCase(restauranteRepository);
    }

    @Test
    void deveDeletarRestauranteQuandoIdExistente() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
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
                usuarioDonoRestaurante);

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));

        deletarRestauranteUseCase.executar(id);

        verify(restauranteRepository).deletarRestaurante(id);
    }

    @Test
    void naoDevePermitirDeletarRestauranteQuandoIdNaoExiste(){
        UUID id = UUID.randomUUID();

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deletarRestauranteUseCase.executar(id));

        verify(restauranteRepository, never()).deletarRestaurante(any());
    }
}
