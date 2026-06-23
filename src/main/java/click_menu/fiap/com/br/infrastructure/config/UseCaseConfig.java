package click_menu.fiap.com.br.infrastructure.config;

import click_menu.fiap.com.br.application.usecases.usuarios.AtualizarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.CriarUsuarioUseCase;
import click_menu.fiap.com.br.application.usecases.usuarios.DeletarUsuarioUseCase;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import click_menu.fiap.com.br.infrastructure.mapper.UsuarioMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase (UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        return new CriarUsuarioUseCase(usuarioRepository, usuarioMapper);
    }

    @Bean
    public DeletarUsuarioUseCase deletarUsuarioUseCase (UsuarioRepository usuarioRepository) {
        return new DeletarUsuarioUseCase(usuarioRepository);
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase (UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        return new AtualizarUsuarioUseCase(usuarioRepository, usuarioMapper);
    }
}
