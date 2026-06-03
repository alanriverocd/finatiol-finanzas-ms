package com.finatiol.finanzas.repository;

import com.finatiol.finanzas.entity.MovimientoFinancieroEntity;
import com.finatiol.finanzas.entity.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimientoFinancieroRepository extends JpaRepository<MovimientoFinancieroEntity, Long> {

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM MovimientoFinancieroEntity m " +
           "WHERE m.tipo = :tipo AND (:tenantId IS NULL OR m.tenantId = :tenantId)")
    BigDecimal sumMontoByTipoAndTenantId(
            @Param("tipo") TipoMovimiento tipo,
            @Param("tenantId") String tenantId);

    Optional<MovimientoFinancieroEntity> findByReferenciaAndTenantId(String referencia, String tenantId);

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM MovimientoFinancieroEntity m " +
           "WHERE m.tipo = :tipo AND m.fecha >= :desde AND m.fecha < :hasta " +
           "AND (:tenantId IS NULL OR m.tenantId = :tenantId)")
    BigDecimal sumMontoByTipoAndFechaBetweenAndTenantId(
            @Param("tipo") TipoMovimiento tipo,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            @Param("tenantId") String tenantId);

    @Query("SELECT m FROM MovimientoFinancieroEntity m " +
           "WHERE m.tipo = :tipo AND m.fecha >= :desde AND m.fecha < :hasta " +
           "AND (:tenantId IS NULL OR m.tenantId = :tenantId)")
    List<MovimientoFinancieroEntity> findByTipoAndFechaBetweenAndTenantId(
            @Param("tipo") TipoMovimiento tipo,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            @Param("tenantId") String tenantId);

    @Modifying
    @Query("DELETE FROM MovimientoFinancieroEntity m " +
           "WHERE m.tipo = :tipo AND m.fecha >= :desde AND m.fecha < :hasta " +
           "AND (:tenantId IS NULL OR m.tenantId = :tenantId)")
    void deleteByTipoAndFechaBetweenAndTenantId(
            @Param("tipo") TipoMovimiento tipo,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            @Param("tenantId") String tenantId);
}
