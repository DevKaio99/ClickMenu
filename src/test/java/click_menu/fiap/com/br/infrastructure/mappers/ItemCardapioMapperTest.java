package click_menu.fiap.com.br.infrastructure.mappers;

import click_menu.fiap.com.br.domain.entities.ItemCardapio;
import click_menu.fiap.com.br.domain.entities.Restaurante;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCardapioMapperTest {

    private final ItemCardapioMapper itemCardapioMapper = new ItemCardapioMapper();

    @Test
    void deveConverterCreateDTOParaEntidade() {
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

        ItemCardapioCreateDTO itemCardapioCreateDTO = new ItemCardapioCreateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante.getId());

        ItemCardapio itemCardapio = itemCardapioMapper.toEntity(itemCardapioCreateDTO, restaurante);

        assertNotNull(itemCardapio.getId());
        assertEquals("Frango a milanesa", itemCardapio.getNome());
        assertEquals(restaurante, itemCardapio.getRestaurante());
    }

    @Test
    void deveConverterEntidadeParaResponseDTO() {
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

        ItemCardapio itemCardapio = new ItemCardapio(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante);

        ItemCardapioResponseDTO resultado = itemCardapioMapper.itemCardapioResponseDTO(itemCardapio);

        assertEquals(itemCardapio.getId(), resultado.id());
        assertEquals("Frango a milanesa", resultado.nome());
        assertEquals(BigDecimal.valueOf(29.90), resultado.preco());
        assertTrue(resultado.consumirApenasRestaurante());
    }
}
