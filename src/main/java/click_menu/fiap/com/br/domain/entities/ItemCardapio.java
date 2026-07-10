package click_menu.fiap.com.br.domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemCardapio {
    protected UUID id;
    protected String nome;
    protected String descricao;
    protected BigDecimal preco;
    protected boolean consumirApenasRestaurante;
    protected String foto;
    protected Restaurante restaurante;

    public ItemCardapio(String nome, String descricao, BigDecimal preco, boolean consumirApenasRestaurante, String foto, Restaurante restaurante) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.consumirApenasRestaurante = consumirApenasRestaurante;
        this.foto = foto;
        this.restaurante = restaurante;

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do item não pode estar em branco");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição não pode estar em branco");
        }

        if (preco == null) {
            throw new IllegalArgumentException("O preço não pode estar em branco");
        }

        if (restaurante == null) {
            throw new IllegalArgumentException("Informe um restaurante");
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isConsumirApenasRestaurante() {
        return consumirApenasRestaurante;
    }

    public void setConsumirApenasRestaurante(boolean consumirApenasRestaurante) {
        this.consumirApenasRestaurante = consumirApenasRestaurante;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }
}
