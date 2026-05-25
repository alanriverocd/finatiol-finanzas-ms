package com.finatiol.finanzas.service;

import com.finatiol.finanzas.dto.MovimientoRequestDTO;
import com.finatiol.finanzas.entity.MovimientoFinancieroEntity;

import java.math.BigDecimal;
import java.util.List;

public interface MovimientoFinancieroService {

    MovimientoFinancieroEntity registrar(MovimientoRequestDTO request);

    List<MovimientoFinancieroEntity> obtenerTodos();

    BigDecimal totalIngresos();

    BigDecimal totalEgresos();

    BigDecimal balance();
}
