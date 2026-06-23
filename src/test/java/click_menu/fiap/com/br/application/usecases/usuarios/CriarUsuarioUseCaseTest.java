package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;

    private CriarUsuarioUseCase criarUsuarioUseCase;


    @BeforeEach
    void setUp() {
        criarUsuarioUseCase = new CriarUsuarioUseCase(usuarioRepository, usuarioMapper);

    }

    @Test
    void deveCriarUsuarioQuandoEmailNaoExiste() throws Exception {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("Teste","teste@email.com","123456", LocalDateTime.now(), TipoUsuario.CLIENTE);
        Usuario usuario = new Usuario("Teste","teste@email.com","123456", LocalDateTime.now(), TipoUsuario.CLIENTE);
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuario.getId(),"Teste","teste@email.com", LocalDateTime.now(), TipoUsuario.CLIENTE);

        when(usuarioRepository.validarEmailExistente("teste@email.com")).thenReturn(false);
        when(usuarioMapper.toEntity(usuarioCreateDTO)).thenReturn(usuario);
        when(usuarioRepository.salvarUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        UsuarioResponseDTO resultado = criarUsuarioUseCase.executar(usuarioCreateDTO);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.nome());

        verify(usuarioRepository).salvarUsuario(usuario);

    }

    @Test
    void naoDeveCriarUsuarioQuandoEmailExiste() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("Teste","teste@email.com","123456", LocalDateTime.now(), TipoUsuario.CLIENTE);

        when(usuarioRepository.validarEmailExistente("teste@email.com")).thenReturn(true);

        assertThrows(BusinessException.class,
                () -> criarUsuarioUseCase.executar(usuarioCreateDTO));

        verify(usuarioRepository, never()).salvarUsuario(any());

    }

}
