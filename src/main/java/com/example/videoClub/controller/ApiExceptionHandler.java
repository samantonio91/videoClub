package com.example.videoClub.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/** Normalizes client-facing failures using RFC 9457 Problem Details. */
@RestControllerAdvice
public class ApiExceptionHandler {
    private ResponseEntity<ProblemDetail> problem(
            HttpStatusCode code, String message, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(code, message);
        detail.setInstance(URI.create(request.getRequestURI()));
        return ResponseEntity.status(code).body(detail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> invalidFields(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        ResponseEntity<ProblemDetail> response =
                problem(HttpStatus.BAD_REQUEST, "La solicitud contiene campos inválidos", request);
        List<Map<String, String>> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(field -> Map.of(
                        "field", field.getField(),
                        "message", field.getDefaultMessage() == null ? "Valor inválido" : field.getDefaultMessage()))
                .sorted(Comparator.comparing(entry -> entry.get("field")))
                .toList();
        response.getBody().setProperty("errors", errors);
        return response;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> businessError(
            ResponseStatusException exception, HttpServletRequest request) {
        return problem(exception.getStatusCode(),
                exception.getReason() == null ? "No se pudo procesar la solicitud" : exception.getReason(),
                request);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ProblemDetail> malformedRequest(
            Exception exception, HttpServletRequest request) {
        return problem(HttpStatus.BAD_REQUEST, "Formato o parámetro de solicitud inválido", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> databaseConflict(
            DataIntegrityViolationException exception, HttpServletRequest request) {
        // Do not expose SQL, database names, or constraint details to API callers.
        return problem(HttpStatus.CONFLICT, "La operación entra en conflicto con datos existentes", request);
    }
}
