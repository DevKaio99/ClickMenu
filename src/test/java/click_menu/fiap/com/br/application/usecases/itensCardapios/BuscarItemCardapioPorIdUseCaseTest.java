package click_menu.fiap.com.br.application.usecases.itensCardapios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.repositories.ItemCardapioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.ItemCardapioMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuscarItemCardapioPorIdUseCaseTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;
    @Mock
    private ItemCardapioMapper itemCardapioMapper;

    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

    @BeforeEach
    void setUp() {
        buscarItemCardapioPorIdUseCase = new BuscarItemCardapioPorIdUseCase(itemCardapioRepository, itemCardapioMapper);
    }

    @Test
    void deveBuscarItemCardapioQuandoIdExistente() {
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

        ItemCardapio itemCardapio = new ItemCardapio("Frango a milanesa", "descricao", BigDecimal.valueOf(29.90), true, "/...", restaurante);

        ItemCardapioResponseDTO itemCardapioResponseDTO = new ItemCardapioResponseDTO(
                id, "Frango a milanesa", "descricao", BigDecimal.valueOf(29.90), true, "/...");

        when(itemCardapioRepository.buscarItemCardapioPorId(id)).thenReturn(Optional.of(itemCardapio));
        when(itemCardapioMapper.itemCardapioResponseDTO(itemCardapio)).thenReturn(itemCardapioResponseDTO);

        ItemCardapioResponseDTO resultado = buscarItemCardapioPorIdUseCase.executar(id);

        assertNotNull(resultado);
        assertEquals("Frango a milanesa", resultado.nome());
    }

    @Test
    void naoDeveBuscarItemCardapioQuandoIdInexistente() {
        UUID id = UUID.randomUUID();

        when(itemCardapioRepository.buscarItemCardapioPorId(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> buscarItemCardapioPorIdUseCase.executar(id));
    }
}
