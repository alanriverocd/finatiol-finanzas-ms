package com.finatiol.finanzas.repository;

import com.finatiol.finanzas.entity.MovimientoFinancieroEntity;
import com.finatiol.finanzas.entity.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface MovimientoFinancieroRepository extends JpaRepository<MovimientoFinancieroEntity, Long> {

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM MovimientoFinancieroEntity m WHERE m.tipo = :tipo")
    BigDecimal sumMontoByTipo(TipoMovimiento tipo);
}
