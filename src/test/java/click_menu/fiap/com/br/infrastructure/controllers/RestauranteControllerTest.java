package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.application.usecases.restaurantes.AtualizarRestauranteUsecase;
import click_menu.fiap.com.br.application.usecases.restaurantes.BuscarRestaurantePorIdUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.CriarRestauranteUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.DeletarRestauranteUseCase;
import click_menu.fiap.com.br.application.usecases.restaurantes.ListarRestaurantesUseCase;
import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Restaurante.RestauranteUpdateDTO;
import click_menu.fiap.com.br.infrastructure.security.CustomUserDetailsService;
import click_menu.fiap.com.br.infrastructure.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestauranteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private CriarRestauranteUseCase criarRestauranteUseCase;
    @MockitoBean
    private ListarRestaurantesUseCase listarRestaurantesUseCase;
    @MockitoBean
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    @MockitoBean
    private AtualizarRestauranteUsecase atualizarRestauranteUsecase;
    @MockitoBean
    private DeletarRestauranteUseCase deletarRestauranteUseCase;
    @MockitoBean
    private TokenService tokenService;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveCriarRestauranteQuandoDadosValidos() throws Exception {
        RestauranteCreateDTO restauranteCreateDTO = new RestauranteCreateDTO(
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA),
                UUID.randomUUID());

        RestauranteResponseDTO restauranteResponseDTO = new RestauranteResponseDTO(
                UUID.randomUUID(),
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA, DiasDaSemana.TERCA));

        when(criarRestauranteUseCase.executar(any())).thenReturn(restauranteResponseDTO);

        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeRestaurante").value("RestauranteTeste"));
    }

    @Test
    void deveRetornar400QuandoNomeEmBranco() throws Exception {
        RestauranteCreateDTO restauranteCreateDTO = new RestauranteCreateDTO(
                "",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA),
                UUID.randomUUID());

        mockMvc.perform(post("/api/v1/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteCreateDTO)))
                .andExpect(status().isBadRequest());

        verify(criarRestauranteUseCase, never()).executar(any());
    }

    @Test
    void deveListarRestaurantes() throws Exception {
        RestauranteResponseDTO restauranteResponseDTO = new RestauranteResponseDTO(
                UUID.randomUUID(),
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA));

        when(listarRestaurantesUseCase.executar()).thenReturn(List.of(restauranteResponseDTO));

        mockMvc.perform(get("/api/v1/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void deveBuscarRestaurantePorIdQuandoExistente() throws Exception {
        UUID id = UUID.randomUUID();
        RestauranteResponseDTO restauranteResponseDTO = new RestauranteResponseDTO(
                id,
                "RestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA));

        when(buscarRestaurantePorIdUseCase.executar(id)).thenReturn(restauranteResponseDTO);

        mockMvc.perform(get("/api/v1/restaurantes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeRestaurante").value("RestauranteTeste"));
    }

    @Test
    void deveRetornar404QuandoRestauranteNaoEncontrado() throws Exception {
        UUID id = UUID.randomUUID();

        when(buscarRestaurantePorIdUseCase.executar(id))
                .thenThrow(new ResourceNotFoundException("Restaurante não encontrado"));

        mockMvc.perform(get("/api/v1/restaurantes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarRestauranteQuandoDadosValidos() throws Exception {
        UUID id = UUID.randomUUID();

        RestauranteUpdateDTO restauranteUpdateDTO = new RestauranteUpdateDTO(
                "NovoRestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA),
                UUID.randomUUID());

        RestauranteResponseDTO restauranteResponseDTO = new RestauranteResponseDTO(
                id,
                "NovoRestauranteTeste",
                "Rua de exemplo, 344",
                TipoCozinhaRestaurante.JAPONESA,
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                EnumSet.of(DiasDaSemana.SEGUNDA));

        when(atualizarRestauranteUsecase.executar(eq(id), any())).thenReturn(restauranteResponseDTO);

        mockMvc.perform(put("/api/v1/restaurantes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restauranteUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeRestaurante").value("NovoRestauranteTeste"));
    }

    @Test
    void deveDeletarRestauranteQuandoIdExistente() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/restaurantes/{id}", id))
                .andExpect(status().isOk());

        verify(deletarRestauranteUseCase).executar(id);
    }
}
