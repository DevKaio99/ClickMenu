package click_menu.fiap.com.br.application.usecases.itensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
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
public class DeletarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;
    @Mock

    private DeletarItemCardapioUseCase deletarItemCardapioUseCase;

    @BeforeEach
    void setUp() {
        deletarItemCardapioUseCase = new DeletarItemCardapioUseCase(itemCardapioRepository);
    }

    @Test
    void deveDeletarItemCardapio() {
        UUID id = UUID.randomUUID();

        Usuario usuarioDonoRestaurante = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

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

        when(itemCardapioRepository.buscarItemCardapioPorId(id)).thenReturn(Optional.of(itemCardapio));

        deletarItemCardapioUseCase.executar(id);

        verify(itemCardapioRepository).deletarItemCardapio(id);
    }

    @Test
    void naoDeveDeletarItemCardapio() {
        UUID id = UUID.randomUUID();

        when(itemCardapioRepository.buscarItemCardapioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> deletarItemCardapioUseCase.executar(id));

        verify(itemCardapioRepository, never()).deletarItemCardapio(any());
    }
}
