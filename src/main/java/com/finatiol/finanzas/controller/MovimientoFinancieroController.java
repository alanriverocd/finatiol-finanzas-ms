package com.finatiol.finanzas.controller;

import com.finatiol.common.constants.finanzas.SuccessCodes;
import com.finatiol.common.constants.finanzas.SuccessMessages;
import com.finatiol.common.response.SuccessResponse;
import com.finatiol.finanzas.dto.MovimientoRequestDTO;
import com.finatiol.finanzas.entity.MovimientoFinancieroEntity;
import com.finatiol.finanzas.service.MovimientoFinancieroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/finanzas")
public class MovimientoFinancieroController {

    private final MovimientoFinancieroService service;

    public MovimientoFinancieroController(MovimientoFinancieroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<MovimientoFinancieroEntity>> registrar(
            @Valid @RequestBody MovimientoRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(
                        SuccessCodes.MOVIMIENTO_CREADO,
                        SuccessMessages.MOVIMIENTO_CREADO,
                        201,
                        service.registrar(request)));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<MovimientoFinancieroEntity>>> obtenerTodos() {
        return ResponseEntity.ok(new SuccessResponse<>(
                SuccessCodes.MOVIMIENTOS_OBTENIDOS,
                SuccessMessages.MOVIMIENTOS_OBTENIDOS,
                200,
                service.obtenerTodos()));
    }

    @GetMapping("/total-ingresos")
    public ResponseEntity<SuccessResponse<BigDecimal>> totalIngresos() {
        return ResponseEntity.ok(new SuccessResponse<>(
                SuccessCodes.TOTAL_INGRESOS_OBTENIDO,
                SuccessMessages.TOTAL_INGRESOS_OBTENIDO,
                200,
                service.totalIngresos()));
    }

    @GetMapping("/total-egresos")
    public ResponseEntity<SuccessResponse<BigDecimal>> totalEgresos() {
        return ResponseEntity.ok(new SuccessResponse<>(
                SuccessCodes.TOTAL_EGRESOS_OBTENIDO,
                SuccessMessages.TOTAL_EGRESOS_OBTENIDO,
                200,
                service.totalEgresos()));
    }

    @GetMapping("/balance")
    public ResponseEntity<SuccessResponse<BigDecimal>> balance() {
        return ResponseEntity.ok(new SuccessResponse<>(
                SuccessCodes.BALANCE_OBTENIDO,
                SuccessMessages.BALANCE_OBTENIDO,
                200,
                service.balance()));
    }

    @GetMapping("/resumen")
    public ResponseEntity<BigDecimal> resumenBalance() {
        return ResponseEntity.ok(service.balance());
    }
}
