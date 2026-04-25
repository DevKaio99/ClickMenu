package com.fiap.ClickMenu.Exceptions;

public record BusinessError(
        int status,
        String error,
        String message) {
}
