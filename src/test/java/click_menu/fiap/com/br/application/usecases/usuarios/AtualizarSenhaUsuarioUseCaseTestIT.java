package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioUpdatePassDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class AtualizarSenhaUsuarioUseCaseTestIT {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AtualizarSenhaUsuarioUseCase atualizarSenhaUsuarioUseCase;

    @Test
    void deveAtualizarSenha() {
        Usuario usuario = usuarioRepository.salvarUsuario(new Usuario(
                "Teste",
                "teste@email.com",
                passwordEncoder.encode("123456"),
                LocalDateTime.now(),
                TipoUsuario.CLIENTE));
        UUID id = usuario.getId();

    UsuarioUpdatePassDTO usuarioUpdatePassDTO = new UsuarioUpdatePassDTO(
            "123456",
            "654321"
    );

       atualizarSenhaUsuarioUseCase.executar(id, usuarioUpdatePassDTO);

       Usuario usuarioComSenhaAtualizado = usuarioRepository.buscarUsuarioPorId(id).orElseThrow();
        assertTrue(passwordEncoder.matches("654321", usuarioComSenhaAtualizado.getSenha()
        ));
    }

    @Test
    void naoDeveAtualizarSenhaQuandoSenhaAtualErrada() {
        Usuario usuario = usuarioRepository.salvarUsuario(new Usuario(
                "Teste",
                "teste@email.com",
                passwordEncoder.encode("123456"),
                LocalDateTime.now(),
                TipoUsuario.CLIENTE));
        UUID id = usuario.getId();

        UsuarioUpdatePassDTO usuarioUpdatePassDTO = new UsuarioUpdatePassDTO(
                "999999",
                "654321"
        );

        assertThrows(BusinessException.class,
                () -> atualizarSenhaUsuarioUseCase.executar(id, usuarioUpdatePassDTO));

    }
}

