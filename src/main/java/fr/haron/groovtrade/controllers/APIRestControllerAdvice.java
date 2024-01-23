package fr.haron.groovtrade.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class APIRestControllerAdvice {

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>("Token JWT expiré", HttpStatus.UNAUTHORIZED);
    }
}
