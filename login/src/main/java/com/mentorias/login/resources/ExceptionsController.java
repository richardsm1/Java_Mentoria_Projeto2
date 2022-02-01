package com.mentorias.login.resources;

import com.mentorias.login.exceptions.*;
import com.mentorias.login.utils.ErroPadrao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Set;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroPadrao> credenciaisInvalidasException(CredenciaisInvalidasException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(UsuarioSemPermissaoException.class)
    public ResponseEntity<ErroPadrao> usuarioSemPermissaoException(UsuarioSemPermissaoException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<ErroPadrao> usuarioJaExisteException(UsuarioJaExisteException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> usuarioNaoEncontradoException(UsuarioNaoEncontradoException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroPadrao> constraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        Set<ConstraintViolation<?>> conjuntoViolacoes = e.getConstraintViolations();
        StringBuilder violacoes = new StringBuilder();

        for (ConstraintViolation<?> v : conjuntoViolacoes) {
            violacoes.append(v.getMessageTemplate()).append("; ");
        }

        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                violacoes.toString().trim());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroPadrao> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Parâmetro de requisição inválido");
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroPadrao> missingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Parâmetro de requisição ausente");
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErroPadrao> authenticationFailedException(AuthenticationFailedException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroPadrao> httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Erro na leitura da requisição");
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(FuncaoInvalidaException.class)
    public ResponseEntity<ErroPadrao> funcaoInvalidaException(FuncaoInvalidaException e, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }
}
