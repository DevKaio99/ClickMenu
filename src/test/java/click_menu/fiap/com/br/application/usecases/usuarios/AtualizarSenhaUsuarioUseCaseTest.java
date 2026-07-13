package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdatePassDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AtualizarSenhaUsuarioUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase;

    @BeforeEach
    void setUp() {
        atualizarSenhaUsuarioUseCase = new AtualizarSenhaUsuarioUseCase(usuarioRepository, usuarioMapper, passwordEncoder);
    }

    @Test
    void devePermitirAtualizarSenha(){
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "senhaAntigaHash",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        UsuarioUpdatePassDTO usuarioUpdatePassDTO = new UsuarioUpdatePassDTO(
                "123456",
                "654321"
        );

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                usuario.getId(),
                "Teste",
                "teste@email.com",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE);

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("123456", "senhaAntigaHash"))
                .thenReturn(true);
        when(passwordEncoder.encode("654321")).thenReturn("novaSenhaHash");
        when(usuarioRepository.atualizarSenha(usuario)).thenReturn(usuario);
        when(usuarioMapper.usuarioResponseDTO(usuario)).thenReturn(usuarioResponseDTO);

        UsuarioResponseDTO resultado = atualizarSenhaUsuarioUseCase.executar(id, usuarioUpdatePassDTO);

        assertEquals("novaSenhaHash", usuario.getSenha());

        verify(passwordEncoder).matches("123456", "senhaAntigaHash");
        verify(passwordEncoder).encode("654321");
        verify(usuarioRepository).atualizarSenha(usuario);

    }

    @Test
    void deveLancarExceptionQuandoSenhaAtualIncorreta() {
        UUID id = UUID.randomUUID();

        Usuario usuario = new Usuario(
                "Teste",
                "teste@email.com",
                "senhaHash",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE
        );

        UsuarioUpdatePassDTO dto = new UsuarioUpdatePassDTO(
                "senhaErrada",
                "654321"
        );

        when(usuarioRepository.buscarUsuarioPorId(id)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "senhaHash")).thenReturn(false);

       assertThrows(BusinessException.class,
                () -> atualizarSenhaUsuarioUseCase.executar(id, dto));

        verify(usuarioRepository, never()).atualizarSenha(any());
    }

    @Test
    void deveLancarExceptionQuandoUsuarioInexistente() {

        UUID id = UUID.randomUUID();

        UsuarioUpdatePassDTO dto = new UsuarioUpdatePassDTO(
                "123456",
                "654321"
        );

        when(usuarioRepository.buscarUsuarioPorId(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> atualizarSenhaUsuarioUseCase.executar(id, dto));

        verify(usuarioRepository, never()).atualizarSenha(any());
    }
}
