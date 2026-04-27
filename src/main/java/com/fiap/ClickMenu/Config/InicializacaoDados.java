package com.fiap.ClickMenu.Config;

import com.fiap.ClickMenu.Entities.TipoUsuario;
import com.fiap.ClickMenu.Entities.Usuario;
import com.fiap.ClickMenu.Repositories.UsuarioRepository;
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
            if (usuarioRepository.findByEmailIgnoreCase("adm@email.com").isEmpty()) {

                Usuario usuarioInicial = new Usuario();
                usuarioInicial.setNome("Admin");
                usuarioInicial.setEmail("admin@email.com");
                usuarioInicial.setSenha(passwordEncoder.encode("fiap123"));
                usuarioInicial.setEndereco("Rua de exemplo, 0");
                usuarioInicial.setDataUltimaAlteracao(LocalDateTime.now());
                usuarioInicial.setTipo(TipoUsuario.valueOf("CLIENTE"));

                usuarioRepository.save(usuarioInicial);

            }
        };
    }
}
