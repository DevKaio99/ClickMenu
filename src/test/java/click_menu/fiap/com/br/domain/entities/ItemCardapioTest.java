package click_menu.fiap.com.br.domain.entities;

import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCardapioTest {

    @Test
    @DisplayName("Deve criar um item valido com id gerado automaticamente")
    void deveCriarItem() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
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
                usuarioTeste);

        ItemCardapio itemCardapio = new ItemCardapio(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                restaurante);

        assertNotNull(itemCardapio.getId());
        assertNotNull(itemCardapio.nome);
        assertEquals("Frango a milanesa", itemCardapio.getNome());
        assertNotNull(itemCardapio.preco);
        assertNotNull(itemCardapio.restaurante);

    }

    @Test
    @DisplayName("Não deve criar item quando nome está ausente")
    void naoDeveCriarItemQuandoNomeNulloOuEmBranco() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
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
                usuarioTeste);

        assertThrows(IllegalArgumentException.class,
                () -> new ItemCardapio(
                        "",
                        "frango empanado com farinha",
                        BigDecimal.valueOf(29.90),
                        true,
                        "/...",
                        restaurante));

    }

    @Test
    @DisplayName("Não deve criar item quando a descrição está ausente")
    void naoDeveCriarItemQuandoDescricaoNullaOuEmBranco() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
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
                usuarioTeste);

        assertThrows(IllegalArgumentException.class,
                () -> new ItemCardapio(
                        "Frango a milanesa",
                        "",
                        BigDecimal.valueOf(29.90),
                        true,
                        "/...",
                        restaurante));
    }

    @Test
    @DisplayName("Não deve criar item quando preço está ausente")
    void naoDeveCriarItemQuandoPrecoNullo() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
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
                usuarioTeste);

        assertThrows(IllegalArgumentException.class,
                () -> new ItemCardapio(
                        "Frango a milanesa",
                        "frango empanado com farinha",
                        null,
                        true,
                        "/...",
                        restaurante));

    }

    @Test
    @DisplayName("Não deve criar item quando não foi informado um restaurante")
    void naoDeveCriarItemQuandoRestauranteNullo() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
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
                usuarioTeste);

        assertThrows(IllegalArgumentException.class,
                () -> new ItemCardapio(
                        "Frango a milanesa",
                        "frango empanado com farinha",
                        BigDecimal.valueOf(29.90),
                        true,
                        "/...",
                        null));

    }
}
