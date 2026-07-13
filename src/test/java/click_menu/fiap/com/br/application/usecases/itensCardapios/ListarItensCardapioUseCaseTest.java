package click_menu.fiap.com.br.application.usecases.itensCardapios;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarItensCardapioUseCaseTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;
    @Mock
    private ItemCardapioMapper itemCardapioMapper;

    private ListarItensCardapioUseCase listarItensCardapioUseCase;

    @BeforeEach
    void setUp() {
        listarItensCardapioUseCase = new ListarItensCardapioUseCase(itemCardapioRepository, itemCardapioMapper);
    }

    private ItemCardapio criarItemCardapio(String nome) {
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

        return new ItemCardapio(nome, "descricao", BigDecimal.valueOf(29.90), true, "/...", restaurante);
    }

    @Test
    void deveListarTodosOsItensDeCardapioCadastrados() {
        ItemCardapio itemA = criarItemCardapio("ItemA");
        ItemCardapio itemB = criarItemCardapio("ItemB");

        ItemCardapioResponseDTO responseA = new ItemCardapioResponseDTO(
                itemA.getId(), "ItemA", "descricao", BigDecimal.valueOf(29.90), true, "/...");
        ItemCardapioResponseDTO responseB = new ItemCardapioResponseDTO(
                itemB.getId(), "ItemB", "descricao", BigDecimal.valueOf(29.90), true, "/...");

        when(itemCardapioRepository.listarItensCardapio()).thenReturn(List.of(itemA, itemB));
        when(itemCardapioMapper.itemCardapioResponseDTO(itemA)).thenReturn(responseA);
        when(itemCardapioMapper.itemCardapioResponseDTO(itemB)).thenReturn(responseB);

        List<ItemCardapioResponseDTO> resultado = listarItensCardapioUseCase.executar();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(responseA));
        assertTrue(resultado.contains(responseB));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverItensCadastrados() {
        when(itemCardapioRepository.listarItensCardapio()).thenReturn(List.of());

        List<ItemCardapioResponseDTO> resultado = listarItensCardapioUseCase.executar();

        assertTrue(resultado.isEmpty());
    }
}
