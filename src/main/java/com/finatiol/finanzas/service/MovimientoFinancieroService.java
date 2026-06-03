package com.finatiol.finanzas.service;

import com.finatiol.finanzas.dto.MovimientoRequestDTO;
import com.finatiol.finanzas.dto.ResumenMensualDTO;
import com.finatiol.finanzas.entity.MovimientoFinancieroEntity;

import java.math.BigDecimal;
import java.util.List;

public interface MovimientoFinancieroService {

    MovimientoFinancieroEntity registrar(MovimientoRequestDTO request);

    List<MovimientoFinancieroEntity> obtenerTodos();

    BigDecimal totalIngresos();

    BigDecimal totalEgresos();

    BigDecimal balance();

    /** Archiva ingresos del mes actual en historial y los elimina (egresos se conservan). */
    ResumenMensualDTO cerrarMes();

    /** Devuelve el historial de cierres mensuales ordenado por año/mes desc. */
    List<ResumenMensualDTO> historialMensual();
}
