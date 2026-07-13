package click_menu.fiap.com.br.infrastructure.controllers;

import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioAutenticacaoDTO;
import click_menu.fiap.com.br.infrastructure.security.CustomUserDetailsService;
import click_menu.fiap.com.br.infrastructure.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private AuthenticationManager authenticationManager;
    @MockitoBean
    private TokenService tokenService;
    @MockitoBean
    private UsuarioRepository usuarioRepository;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void deveAutenticarUsuarioQuandoCredenciaisValidas() throws Exception {
        UsuarioAutenticacaoDTO usuarioAutenticacaoDTO = new UsuarioAutenticacaoDTO("teste@email.com", "123456");

        UserDetails userDetails = User.builder()
                .username("teste@email.com")
                .password("123456")
                .authorities(List.of())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, List.of());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenService.gerarToken(userDetails)).thenReturn("token-gerado");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioAutenticacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-gerado"));
    }
}
