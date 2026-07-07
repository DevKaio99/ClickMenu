package click_menu.fiap.com.br.application.usecases.restaurantes;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.RestauranteResponseDTO;
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
public class CriarRestauranteUseCaseTest {

    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private RestauranteMapper restauranteMapper;
    @Mock
    private UsuarioRepository usuarioRepository;

    private CriarRestauranteUseCase criarRestauranteUseCase;


    @BeforeEach
    void setUp() {
        criarRestauranteUseCase = new CriarRestauranteUseCase(restauranteRepository, restauranteMapper, usuarioRepository);
    }

    @Test
    void deveCriarRestauranteQuandoNomeEEnderecoNaoExistentes() {
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
        RestauranteCreateDTO restauranteCreateDTO =  new RestauranteCreateDTO (
                "RestauranteTeste",
                "Rua de exemplo, 123",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());
        RestauranteResponseDTO restauranteResponseDTO = new RestauranteResponseDTO(
                restaurante.getId(),
                "RestauranteTeste",
                "Rua de exemplo, 123",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA));

        when(restauranteRepository.validarNomeEEnderecoExistente("RestauranteTeste", "Rua de exemplo, 123")).thenReturn(false);
        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuarioDonoRestaurante));
        when(restauranteMapper.toEntity(restauranteCreateDTO, usuarioDonoRestaurante)).thenReturn(restaurante);
        when(restauranteRepository.salvarRestaurante(restaurante)).thenReturn(restaurante);
        when(restauranteMapper.restauranteResponseDTO(restaurante)).thenReturn(restauranteResponseDTO);

        RestauranteResponseDTO resultado = criarRestauranteUseCase.executar(restauranteCreateDTO);

        assertNotNull(resultado);
        assertEquals("RestauranteTeste", resultado.nomeRestaurante());

        verify(restauranteRepository).salvarRestaurante(restaurante);


    }

    @Test
    void naoDeveCriarRestauranteQuandoNomeEEnderecoExistem() {
        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE);
        RestauranteCreateDTO restauranteCreateDTO =  new RestauranteCreateDTO (
                "RestauranteTeste",
                "Rua de exemplo, 123",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioDonoRestaurante.getId());

        when(restauranteRepository.validarNomeEEnderecoExistente(restauranteCreateDTO.nomeRestaurante(), restauranteCreateDTO.enderecoRestaurante())).thenReturn(true);

        assertThrows(BusinessException.class,
                () -> criarRestauranteUseCase.executar(restauranteCreateDTO));

        verify(restauranteRepository, never()).salvarRestaurante(any());

    }



}
