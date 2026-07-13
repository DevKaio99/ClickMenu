package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.application.usecases.itensCardapios.AtualizarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.CriarItemCardapioUseCase;
import click_menu.fiap.com.br.application.usecases.itensCardapios.DeletarItemCardapioUseCase;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.ItemCardapio.ItemCardapioUpdateDTO;
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

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemCardapioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ItemCardapioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private CriarItemCardapioUseCase criarItemCardapioUseCase;
    @MockitoBean
    private AtualizarItemCardapioUseCase atualizarItemCardapioUseCase;
    @MockitoBean
    private DeletarItemCardapioUseCase deletarItemCardapioUseCase;
    @MockitoBean
    private TokenService tokenService;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveCriarItemCardapioQuandoDadosValidos() throws Exception {
        ItemCardapioCreateDTO itemCardapioCreateDTO = new ItemCardapioCreateDTO(
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                UUID.randomUUID());

        ItemCardapioResponseDTO itemCardapioResponseDTO = new ItemCardapioResponseDTO(
                UUID.randomUUID(),
                "Frango a milanesa",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...");

        when(criarItemCardapioUseCase.executar(any())).thenReturn(itemCardapioResponseDTO);

        mockMvc.perform(post("/api/v1/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemCardapioCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Frango a milanesa"));
    }

    @Test
    void deveRetornar400QuandoNomeEmBranco() throws Exception {
        ItemCardapioCreateDTO itemCardapioCreateDTO = new ItemCardapioCreateDTO(
                "",
                "frango empanado com farinha",
                BigDecimal.valueOf(29.90),
                true,
                "/...",
                UUID.randomUUID());

        mockMvc.perform(post("/api/v1/item-cardapio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemCardapioCreateDTO)))
                .andExpect(status().isBadRequest());

        verify(criarItemCardapioUseCase, never()).executar(any());
    }

    @Test
    void deveAtualizarItemCardapioQuandoDadosValidos() throws Exception {
        UUID id = UUID.randomUUID();

        ItemCardapioUpdateDTO itemCardapioUpdateDTO = new ItemCardapioUpdateDTO(
                "Frango a parmegiana",
                "frango com queijo",
                BigDecimal.valueOf(32.90),
                true,
                "/...",
                UUID.randomUUID());

        ItemCardapioResponseDTO itemCardapioResponseDTO = new ItemCardapioResponseDTO(
                id,
                "Frango a parmegiana",
                "frango com queijo",
                BigDecimal.valueOf(32.90),
                true,
                "/...");

        when(atualizarItemCardapioUseCase.executar(eq(id), any())).thenReturn(itemCardapioResponseDTO);

        mockMvc.perform(put("/api/v1/item-cardapio/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemCardapioUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Frango a parmegiana"));
    }

    @Test
    void deveDeletarItemCardapioQuandoIdExistente() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/item-cardapio/{id}", id))
                .andExpect(status().isOk());

        verify(deletarItemCardapioUseCase).executar(id);
    }
}
