package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.ResourceNotFoundException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdateDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AtualizarUsuarioUseCaseTestIT {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    @Test
    void deveAtualizarUsuario() {
        Usuario usuario = usuarioRepository.salvarUsuario(new Usuario(
                "Teste",
                "teste@email.com",
                "123456",
                LocalDateTime.now(),
                TipoUsuario.CLIENTE));
        UUID id = usuario.getId();

        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "NomeNovoTeste",
                "teste@email.com",
                TipoUsuario.CLIENTE);

        atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO);

        Usuario usuarioAtualizado = usuarioRepository.buscarUsuarioPorId(id).orElseThrow();
        assertEquals("NomeNovoTeste", usuarioAtualizado.getNome());
    }

    @Test
    void deveLancarExceptionQuandoIdNaoEncontrado() {
        UUID id = UUID.randomUUID();
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO(
                "NomeNovoTeste",
                "teste@email.com",
                TipoUsuario.CLIENTE);

        assertThrows(ResourceNotFoundException.class,
                () -> atualizarUsuarioUseCase.executar(id, usuarioUpdateDTO));
    }
}
