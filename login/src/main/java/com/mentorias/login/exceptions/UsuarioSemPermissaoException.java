package com.mentorias.login.exceptions;

public class UsuarioSemPermissaoException extends RuntimeException {
    public UsuarioSemPermissaoException(String message) {
        super(message);
    }
}
