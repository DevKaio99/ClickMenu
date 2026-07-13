package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class DeletarRestauranteUseCaseTestIT {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    private DeletarRestauranteUseCase deletarRestauranteUseCase;

    @Test
    void deveDeletarRestauranteComIdExistente() {
        TipoUsuario tipoDonoRestaurante = tipoUsuarioRepository.listarTiposUsuario().stream()
                .filter(tipo -> tipo.getNomeTipo().equals("DONO_RESTAURANTE"))
                .findFirst()
                .orElseGet(() -> tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("DONO_RESTAURANTE")));

        Usuario usuarioDonoRestaurante = usuarioRepository.salvarUsuario(new Usuario(                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoDonoRestaurante));

        Restaurante restaurante = restauranteRepository.salvarRestaurante (new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante));

        UUID id = restaurante.getId();

        assertTrue(restauranteRepository.buscarRestaurantePorId(id).isPresent());

        deletarRestauranteUseCase.executar(id);

        assertTrue(restauranteRepository.buscarRestaurantePorId(id).isEmpty());

    }

    @Test
    void naoDevePermitirDeletarRestauranteQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class,
                () -> deletarRestauranteUseCase.executar(id));
    }


}
