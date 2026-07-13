package click_menu.fiap.com.br.infrastructure.config;

import click_menu.fiap.com.br.domain.entities.Usuario;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;
import click_menu.fiap.com.br.domain.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class InicializacaoDados {

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (usuarioRepository.findByEmailIgnoreCase("admin@email.com").isEmpty()) {

                Usuario usuarioInicial = new Usuario(
                "Admin",
                "admin@email.com",
                passwordEncoder.encode("fiap123"),
                LocalDateTime.now(),
                TipoUsuario.valueOf("ADMIN")
                );

                usuarioRepository.salvarUsuario(usuarioInicial);

            }
        };
    }
}
