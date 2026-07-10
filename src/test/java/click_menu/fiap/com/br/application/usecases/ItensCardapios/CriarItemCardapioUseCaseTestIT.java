package click_menu.fiap.com.br.application.usecases.ItensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.domain.repositories.RestauranteRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;
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
public class CriarItemCardapioUseCaseTestIT {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;
    @Autowired
    private ItemCardapioMapper itemCardapioMapper;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CriarItemCardapioUseCase criarItemCardapioUseCase;

    @Test
    void deveCriarItemCardapio() {

        Usuario usuarioTeste =  new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.DONO_RESTAURANTE);

        usuarioRepository.salvarUsuario(usuarioTeste);

        Restaurante restaurante = new Restaurante(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.now(),
                LocalTime.now(),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                usuarioTeste);

        restauranteRepository.salvarRestaurante(restaurante);


        ItemCardapioCreateDTO itemCardapioCreateDTO = new ItemCardapioCreateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante.getId());

        ItemCardapioResponseDTO resultado = criarItemCardapioUseCase.executar(itemCardapioCreateDTO);

        assertNotNull(resultado.id());
        assertEquals("Frango a milanesa", resultado.nome());
    }

    @Test
    void naoDeveCriarItemCardapioRestauranteNaoEncontrado() {
        UUID id = UUID.randomUUID();

        ItemCardapioCreateDTO itemCardapioCreateDTO = new ItemCardapioCreateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                id);

        assertThrows(ResourceNotFoundException.class,
                () -> criarItemCardapioUseCase.executar(itemCardapioCreateDTO));
    }

}
