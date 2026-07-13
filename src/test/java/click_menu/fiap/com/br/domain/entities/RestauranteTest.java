package click_menu.fiap.com.br.domain.entities;

import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteTest {

    @Test
    @DisplayName("Deve criar um restaurante valido com id gerado automaticamente")
    void deveCriarRestauranteValido() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
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
                usuarioTeste);

        assertNotNull(restaurante.getId());
        assertEquals("RestauranteTeste", restaurante.getNomeRestaurante());
        assertNotNull(restaurante.getEnderecoRestaurante());
        assertEquals(TipoCozinhaRestaurante.JAPONESA, restaurante.getTipoCozinha());
        assertNotNull(restaurante.getHorarioAbertura());
        assertEquals(EnumSet.of(DiasDaSemana.SEGUNDA,
                                DiasDaSemana.TERCA,
                                DiasDaSemana.QUARTA,
                                DiasDaSemana.QUINTA,
                                DiasDaSemana.SEXTA), restaurante.diasFuncionamento);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o nome do restaurante é nulo ou em branco")
    void naoDevePermitirNomeNulo() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        assertThrows(IllegalArgumentException.class,
                () -> new Restaurante(
                        "",
                        "Rua de exemplo, 344",
                        TipoCozinhaRestaurante.JAPONESA,
                        LocalTime.now(),
                        LocalTime.now(),
                        EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                        usuarioTeste
                ));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o endereço é nulo ou em branco")
    void naoDevePermitirEnderecoNulo() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        assertThrows(IllegalArgumentException.class,
                () -> new Restaurante (
                        "RestauranteTeste",
                        "",
                        TipoCozinhaRestaurante.JAPONESA,
                        LocalTime.now(),
                        LocalTime.now(),
                        EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                        usuarioTeste
                ));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o tipo da cozinha é nulo")
    void naoDevePermitirTipoCozinhaNulo() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        assertThrows(IllegalArgumentException.class,
                () -> new Restaurante (
                        "RestauranteTeste",
                        "Rua de exemplo, 344",
                        null,
                        LocalTime.now(),
                        LocalTime.now(),
                        EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                        usuarioTeste
                ));
    }

    @Test
    @DisplayName("Deve lançara exceção quando o horário de funcionameno é nulo")
    void naoDevePermitirHorarioFuncionamentoNulo() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        assertThrows(IllegalArgumentException.class,
                () -> new Restaurante (
                        "RestauranteTeste",
                        "Rua de exemplo, 344",
                        TipoCozinhaRestaurante.JAPONESA,
                        null,
                        null,
                        EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                        usuarioTeste
                ));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando dias de funcionamento é nulo")
    void naoDevePermitirDiasFuncionamentoNulo(){
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        assertThrows(IllegalArgumentException.class,
                () -> new Restaurante (
                        "RestauranteTeste",
                        "Rua de exemplo, 344",
                        TipoCozinhaRestaurante.JAPONESA,
                        LocalTime.now(),
                        LocalTime.now(),
                        null,
                        usuarioTeste
                ));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando dono do restaurante é nulo")
    void naoDevePermitirDonoRestauranteNulo() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("DONO_RESTAURANTE"));

        assertThrows(IllegalArgumentException.class,
                () -> new Restaurante (
                        "RestauranteTeste",
                        "Rua de exemplo, 344",
                        TipoCozinhaRestaurante.JAPONESA,
                        LocalTime.now(),
                        LocalTime.now(),
                        EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                        null
                ));
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando o dono do restaurante não está registrado como DONO_RESTAURANTE")
    void naoDevePermitirDonoRestauranteSemTipoDonoRestaurante() {
        Usuario usuarioTeste = new Usuario(
                "Teste","teste@email.com",
                "123456",
                LocalDateTime.now(),
                new TipoUsuario("CLIENTE"));

        assertThrows(IllegalArgumentException.class,
                () -> new Restaurante (
                        "RestauranteTeste",
                        "Rua de exemplo, 344",
                        TipoCozinhaRestaurante.JAPONESA,
                        LocalTime.now(),
                        LocalTime.now(),
                        EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA, DiasDaSemana.QUARTA, DiasDaSemana.QUINTA, DiasDaSemana.SEXTA),
                        usuarioTeste));
    }
}
