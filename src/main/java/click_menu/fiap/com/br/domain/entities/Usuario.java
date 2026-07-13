package click_menu.fiap.com.br.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Usuario {

    protected UUID id;
    protected String nome;
    protected String email;
    protected String senha;
    protected LocalDateTime dataUltimaAlteracao;
    protected TipoUsuario tipo;


    public Usuario (String nome, String email, String senha, LocalDateTime dataUltimaAlteracao, TipoUsuario tipo) throws IllegalArgumentException {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.tipo = tipo;

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode estar em branco");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (senha == null || senha.length()<6) {
            throw new IllegalArgumentException("A senha deve conter no mínimo 6 caracteres");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Informe se você é cliente ou Dono de Restaurante");
        }

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}