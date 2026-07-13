package click_menu.fiap.com.br.domain.entities;

import java.util.UUID;

public class TipoUsuario {

    protected UUID id;
    protected String nomeTipo;

    public TipoUsuario(String nomeTipo) throws IllegalArgumentException {
        this.id = UUID.randomUUID();
        this.nomeTipo = nomeTipo;

        if (nomeTipo == null || nomeTipo.isBlank()) {
            throw new IllegalArgumentException("O nome do tipo não pode estar em branco");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }
}
