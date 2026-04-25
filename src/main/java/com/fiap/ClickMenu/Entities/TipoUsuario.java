package com.fiap.ClickMenu.Entities;

public enum TipoUsuario {
    CLIENTE,
    DONO_RESTAURANTE;

    public static TipoUsuario fromString(String valor) {
        return TipoUsuario.valueOf(valor.trim().toUpperCase());
    }
}




