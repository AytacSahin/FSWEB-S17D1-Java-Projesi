package com.workintech.rest.exception;

// @ControllerAdvice -> Bu anotasyonun temel amacı, farklı controller sınıflarında veya farklı controller metodlarında meydana gelen hataları tek bir merkezi noktada ele almak ve işlemektir. Böylece hata işleme ve hata mesajları yönetimi daha düzenli bir şekilde yapılabilir.
// @ExceptionHandler -> bu metot belirtilen exception türünde bir hata meydana geldiğinde çalışıyor. Bu sayede hataları yakalayabiliyoruz ve hata mesajları veya durum kodlarıyla yanıt verebiliyoruz.

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        String errorMessage = "Bir hata oluştu 😥";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
