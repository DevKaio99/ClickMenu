package com.fiap.ClickMenu.Exceptions;

public record ErrorResponse(
        int status,
        String error,
        String message) {}