package com.fiap.ClickMenu.Exceptions;

public record ValidationError(
        int status,
        String error,
        String message) {}