package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CriarRestauranteUseCaseTestIT {

    @Autowired
    private CriarRestauranteUseCase criarRestauranteUseCase;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void deveCriarRestauranteNoBancoQuandoNomeEEnderecoInexistente() {
        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE);

        usuarioRepository.salvarUsuario(usuarioDonoRestaurante);

        RestauranteCreateDTO restauranteCreateDTO =  new RestauranteCreateDTO (
                "RestauranteTeste",
                "Rua de exemplo, 123",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        RestauranteResponseDTO resultado = criarRestauranteUseCase.executar(restauranteCreateDTO);

        assertNotNull(resultado.id());
        assertEquals("RestauranteTeste", resultado.nomeRestaurante());

        assertTrue(restauranteRepository.validarNomeEEnderecoExistente("RestauranteTeste", "Rua de exemplo, 123"));
    }

    @Test
    void naoDeveCriarUsuarioQuandoNomeEEnderecoExistentes() {
        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE);
        usuarioRepository.salvarUsuario(usuarioDonoRestaurante);

        RestauranteCreateDTO restauranteCreateDTO =  new RestauranteCreateDTO (
                "RestauranteTeste",
                "Rua de exemplo, 123",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        criarRestauranteUseCase.executar(restauranteCreateDTO);

        assertThrows(BusinessException.class,
                () -> criarRestauranteUseCase.executar(restauranteCreateDTO));

    }


}
