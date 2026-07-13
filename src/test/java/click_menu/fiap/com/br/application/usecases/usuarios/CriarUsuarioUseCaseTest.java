package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.mappers.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    private CriarUsuarioUseCase criarUsuarioUseCase;


    @BeforeEach
    void setUp() {
        criarUsuarioUseCase = new CriarUsuarioUseCase(usuarioRepository, passwordEncoder, usuarioMapper, tipoUsuarioRepository);

    }

    @Test
    void deveCriarUsuarioQuandoEmailNaoExiste() throws Exception {
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("Teste","teste@email.com","123456", LocalDateTime.now(), tipoCliente.getId());
        Usuario usuario = new Usuario("Teste","teste@email.com","123456", LocalDateTime.now(), tipoCliente);
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuario.getId(),"Teste","teste@email.com", LocalDateTime.now(), new TipoUsuarioResponseDTO(tipoCliente.getId(), "CLIENTE"));

        when(usuarioRepository.validarEmailExistente("teste@email.com")).thenReturn(false);
        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(tipoCliente.getId())).thenReturn(Optional.of(tipoCliente));
        when(usuarioMapper.toEntity(usuarioCreateDTO, tipoCliente)).thenReturn(usuario);
        when(usuarioRepository.salvarUsuario(usuario)).thenReturn(usuario);
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        UsuarioResponseDTO resultado = criarUsuarioUseCase.executar(usuarioCreateDTO);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.nome());

        verify(usuarioRepository).salvarUsuario(usuario);

    }

    @Test
    void naoDeveCriarUsuarioQuandoEmailExiste() {
        TipoUsuario tipoCliente = new TipoUsuario("CLIENTE");
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("Teste","teste@email.com","123456", LocalDateTime.now(), tipoCliente.getId());

        when(usuarioRepository.validarEmailExistente("teste@email.com")).thenReturn(true);

        assertThrows(BusinessException.class,
                () -> criarUsuarioUseCase.executar(usuarioCreateDTO));

        verify(usuarioRepository, never()).salvarUsuario(any());

    }

    @Test
    void naoDeveCriarUsuarioQuandoTipoNaoEncontrado() {
        UUID tipoId = UUID.randomUUID();
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("Teste","teste@email.com","123456", LocalDateTime.now(), tipoId);

        when(usuarioRepository.validarEmailExistente("teste@email.com")).thenReturn(false);
        when(tipoUsuarioRepository.buscarTipoUsuarioPorId(tipoId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> criarUsuarioUseCase.executar(usuarioCreateDTO));

        verify(usuarioRepository, never()).salvarUsuario(any());
    }

}
