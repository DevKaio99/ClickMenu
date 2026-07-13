package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class DeletarUsuarioUseCaseTestIT {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    @Autowired
    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @Test
    void  deveDeletarUsuarioComIdExistente() {
        TipoUsuario tipoCliente = tipoUsuarioRepository.listarTiposUsuario().stream()
                .filter(tipo -> tipo.getNomeTipo().equals("CLIENTE"))
                .findFirst()
                .orElseGet(() -> tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("CLIENTE")));

        Usuario usuario = usuarioRepository.salvarUsuario(new Usuario(                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                tipoCliente));
        UUID id = usuario.getId();

        assertTrue(usuarioRepository.buscarUsuarioPorId(id).isPresent());

        deletarUsuarioUseCase.executar(id);

        assertTrue(usuarioRepository.buscarUsuarioPorId(id).isEmpty());

    }

    @Test
    void deveLancarExceptionQuandoIdNaoEncontrado() {
        UUID id = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class,
        () -> deletarUsuarioUseCase.executar(id));
    }
}
