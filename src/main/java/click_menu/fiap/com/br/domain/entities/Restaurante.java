package click_menu.fiap.com.br.domain.entities;

import click_menu.fiap.com.br.domain.enums.DiasDaSemana;
import click_menu.fiap.com.br.domain.enums.TipoCozinhaRestaurante;
import click_menu.fiap.com.br.domain.enums.TipoUsuario;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public class Restaurante {

    protected UUID id;
    protected String nomeRestaurante;
    protected String enderecoRestaurante;
    protected TipoCozinhaRestaurante tipoCozinha;
    protected LocalTime horarioAbertura;
    protected LocalTime horarioFechamento;
    protected Set <DiasDaSemana> diasFuncionamento;
    protected Usuario donoRestaurante;

    public Restaurante(String nomeRestaurante,
                       String enderecoRestaurante,
                       TipoCozinhaRestaurante tipoCozinha,
                       LocalTime horarioAbertura,
                       LocalTime horarioFechamento,
                       Set<DiasDaSemana> diasFuncionamento,
                       Usuario donoRestaurante) {
        this.id = UUID.randomUUID();
        this.nomeRestaurante = nomeRestaurante;
        this.enderecoRestaurante = enderecoRestaurante;
        this.tipoCozinha = tipoCozinha;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.diasFuncionamento = diasFuncionamento;
        this.donoRestaurante = donoRestaurante;

        if (nomeRestaurante == null || nomeRestaurante.isBlank()) {
            throw new IllegalArgumentException("O nome do restaurante não pode estar em branco");
        }
        if (enderecoRestaurante == null || enderecoRestaurante.isBlank()) {
            throw new IllegalArgumentException("O endereço não pode estar em branco");
        }
        if (tipoCozinha == null) {
            throw new IllegalArgumentException("Informe o tipo de cozinha");
        }
        if (horarioAbertura == null || horarioFechamento == null) {
            throw new IllegalArgumentException("Informe o horário de funcionamento completo");
        }

        if (diasFuncionamento == null) {
            throw new IllegalArgumentException("Informe os dias de funcionamento");
        }

        if (donoRestaurante == null || !donoRestaurante.getTipo().equals(TipoUsuario.DONO_RESTAURANTE)) {
            throw new IllegalArgumentException("Informe um usuário que está registrado como Dono de Restaurante");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }

    public String getEnderecoRestaurante() {
        return enderecoRestaurante;
    }

    public void setEnderecoRestaurante(String enderecoRestaurante) {
        this.enderecoRestaurante = enderecoRestaurante;
    }

    public TipoCozinhaRestaurante getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(TipoCozinhaRestaurante tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public LocalTime getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(LocalTime horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public LocalTime getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(LocalTime horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public Set<DiasDaSemana> getDiasFuncionamento() {
        return diasFuncionamento;
    }

    public void setDiasFuncionamento(Set<DiasDaSemana> diasFuncionamento) {
        this.diasFuncionamento = diasFuncionamento;
    }

    public Usuario getDonoRestaurante() {
        return donoRestaurante;
    }

    public void setDonoRestaurante(Usuario donoRestaurante) {
        this.donoRestaurante = donoRestaurante;
    }
}
