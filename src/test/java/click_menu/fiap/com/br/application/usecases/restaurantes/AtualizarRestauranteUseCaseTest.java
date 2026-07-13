package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteUpdateDTO;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarRestauranteUseCaseTest {

    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private RestauranteMapper restauranteMapper;
    @Mock
    private UsuarioRepository usuarioRepository;

    private AtualizarRestauranteUsecase atualizarRestauranteUsecase;

    @BeforeEach
    void setUp() {
        atualizarRestauranteUsecase = new AtualizarRestauranteUsecase(restauranteRepository, restauranteMapper, usuarioRepository);
    }

    @Test
    void devePermitirAtualizarRestaurante() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));
        usuarioDonoRestaurante.setId(id);

        Restaurante restaurante = new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante);

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "NovoRestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        RestauranteResponseDTO restauranteResponseDTO = new RestauranteResponseDTO(
                restaurante.getId(),
                "NovoRestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA)
        );

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));
        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuarioDonoRestaurante));
        when(restauranteRepository.atualizarRestaurante(restaurante)).thenReturn(restaurante);
        when(restauranteMapper.restauranteResponseDTO(restaurante)).thenReturn(restauranteResponseDTO);

        atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO);

        verify(restauranteRepository).atualizarRestaurante(restaurante);

    }

    @Test
    void naoDevePermitirAtualizarRestauranteQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));
        usuarioDonoRestaurante.setId(id);

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());
        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO));

        verify(restauranteRepository, never()).atualizarRestaurante(any());
    }

    @Test
    void naoDeveCriarRestauranteQuandoNomeEEnderecoJaRegistrados() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));
        usuarioDonoRestaurante.setId(id);

        Restaurante restaurante = new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante);

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "NovoRestauranteTeste",
                "Rua de exemplo, 111",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.validarNomeEEnderecoExistente("NovoRestauranteTeste", "Rua de exemplo, 111")).thenReturn(true);

        assertThrows(BusinessException.class, () -> atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO));

        verify(restauranteRepository, never()).atualizarRestaurante(any());
    }

    @Test
    void naoDevePermitirAtualizarRestauranteQuandoUsuarioNaoEstaRegistradoComoDonoRestaurante() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));
        usuarioDonoRestaurante.setId(id);

        Usuario usuarioCliente = new Usuario(
                "Teste", "teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("CLIENTE"));
        usuarioDonoRestaurante.setId(id);

        Restaurante restaurante = new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante);

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "RestauranteTeste",
                "Rua de exemplo, 111",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioCliente.getId());

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));
        when(restauranteRepository.validarNomeEEnderecoExistente("RestauranteTeste", "Rua de exemplo, 111")).thenReturn(false);
        when(usuarioRepository.buscarUsuarioPorId(usuarioCliente.getId())).thenReturn(Optional.of(usuarioCliente));

        assertThrows(BusinessException.class, () -> atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO));

        verify(restauranteRepository, never()).atualizarRestaurante(any());

    }

    @Test
    void naoDevePermitirAtualizarRestauranteQuandoUsuarioInformadoNaoEncontrado() {
        UUID id = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

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

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioId);

        when(restauranteRepository.buscarRestaurantePorId(id)).thenReturn(Optional.of(restaurante));
        when(usuarioRepository.buscarUsuarioPorId(usuarioId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarRestauranteUsecase.executar(id, restauranteUpdateDTO));

        verify(restauranteRepository, never()).atualizarRestaurante(any());
    }

}
