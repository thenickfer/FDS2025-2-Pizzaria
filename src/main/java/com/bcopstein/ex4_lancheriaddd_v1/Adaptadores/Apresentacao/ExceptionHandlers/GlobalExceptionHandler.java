package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.ExceptionHandlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.CardapioNotFoundException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.ClienteNotFoundException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PagamentoErroException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoCancelamentoInvalidoException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoNotFoundException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.PedidoPagamentoInvalidoException;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Exceptions.ProdutoNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ PedidoNotFoundException.class, ClienteNotFoundException.class,
            CardapioNotFoundException.class, ProdutoNotFoundException.class })
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler({ PedidoPagamentoInvalidoException.class, PedidoCancelamentoInvalidoException.class })
    public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(PagamentoErroException.class)
    public ResponseEntity<Map<String, String>> erroPagamento(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handlerGeral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "internal server error"));
    }
}
