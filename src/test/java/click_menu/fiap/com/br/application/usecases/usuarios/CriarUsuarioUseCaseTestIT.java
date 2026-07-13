package click_menu.fiap.com.br.application.usecases.usuarios;

import click_menu.fiap.com.br.application.exceptions.BusinessException;
import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioCreateDTO;
import click_menu.fiap.com.br.infrastructure.dtos.Usuario.UsuarioResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CriarUsuarioUseCaseTestIT {

        @Autowired
        private CriarUsuarioUseCase criarUsuarioUseCase;
        @Autowired
        private UsuarioRepository usuarioRepository;
        @Autowired
        private TipoUsuarioRepository tipoUsuarioRepository;

        @Test
        void deveCriarUsuarioNoBancoQuandoEmailInexistente()  {
            TipoUsuario tipoCliente = tipoUsuarioRepository.listarTiposUsuario().stream()
                .filter(tipo -> tipo.getNomeTipo().equals("CLIENTE"))
                .findFirst()
                .orElseGet(() -> tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("CLIENTE")));

            UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO(
                    "Teste",
                    "teste@email.com",
                    "123456",
                    LocalDateTime.now(),
                    tipoCliente.getId());

            UsuarioResponseDTO resultado = criarUsuarioUseCase.executar(usuarioCreateDTO);

            assertNotNull(resultado.id());
            assertEquals("teste@email.com", resultado.email());

            assertTrue(usuarioRepository.validarEmailExistente("teste@email.com"));

        }

        @Test
        void naoDeveCriarUsuarioQuandoEmailExiste() {
            TipoUsuario tipoCliente = tipoUsuarioRepository.listarTiposUsuario().stream()
                .filter(tipo -> tipo.getNomeTipo().equals("CLIENTE"))
                .findFirst()
                .orElseGet(() -> tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("CLIENTE")));

            UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO(
                    "Teste",
                    "teste@email.com",
                    "123456",
                    LocalDateTime.now(),
                    tipoCliente.getId());

            criarUsuarioUseCase.executar(usuarioCreateDTO);

            assertThrows(BusinessException.class, () -> criarUsuarioUseCase.executar(usuarioCreateDTO));
        }

}
