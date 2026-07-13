package click_menu.fiap.com.br.application.usecases.tiposUsuario;

import click_menu.fiap.com.br.domain.entities.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.TipoUsuarioRepository;
import click_menu.fiap.com.br.infrastructure.dtos.TipoUsuario.TipoUsuarioResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class ListarTiposUsuarioUseCaseTestIT {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private ListarTiposUsuarioUseCase listarTiposUsuarioUseCase;

    @Test
    void deveListarTiposUsuarioCadastradosNoBanco() {
        tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("ADMIN_TESTE"));
        tipoUsuarioRepository.salvarTipoUsuario(new TipoUsuario("CLIENTE_TESTE"));

        List<TipoUsuarioResponseDTO> resultado = listarTiposUsuarioUseCase.executar();

        assertTrue(resultado.stream().anyMatch(tipo -> tipo.nomeTipo().equals("ADMIN_TESTE")));
        assertTrue(resultado.stream().anyMatch(tipo -> tipo.nomeTipo().equals("CLIENTE_TESTE")));
    }
}
