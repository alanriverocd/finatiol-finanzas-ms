package com.finatiol.finanzas.service;

import com.finatiol.common.tenant.TenantContext;
import com.finatiol.finanzas.dto.MovimientoRequestDTO;
import com.finatiol.finanzas.dto.ResumenMensualDTO;
import com.finatiol.finanzas.entity.MovimientoFinancieroEntity;
import com.finatiol.finanzas.entity.ResumenMensualEntity;
import com.finatiol.finanzas.entity.TipoMovimiento;
import com.finatiol.finanzas.repository.MovimientoFinancieroRepository;
import com.finatiol.finanzas.repository.ResumenMensualRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class MovimientoFinancieroServiceImpl implements MovimientoFinancieroService {

    private final MovimientoFinancieroRepository repository;
    private final ResumenMensualRepository resumenRepository;

    public MovimientoFinancieroServiceImpl(MovimientoFinancieroRepository repository,
                                           ResumenMensualRepository resumenRepository) {
        this.repository = repository;
        this.resumenRepository = resumenRepository;
    }

    @Override
    @Transactional
    public MovimientoFinancieroEntity registrar(MovimientoRequestDTO request) {
        String tenantId = TenantContext.getCurrentTenant();
        if (request.getReferencia() != null && !request.getReferencia().isBlank()) {
            return repository.findByReferenciaAndTenantId(request.getReferencia(), tenantId)
                    .orElseGet(() -> guardarMovimiento(request));
        }

        return guardarMovimiento(request);
    }

    private MovimientoFinancieroEntity guardarMovimiento(MovimientoRequestDTO request) {
        MovimientoFinancieroEntity entity = new MovimientoFinancieroEntity();
        entity.setTipo(request.getTipo());
        entity.setConcepto(request.getConcepto());
        entity.setMonto(request.getMonto());
        entity.setReferencia(request.getReferencia());
        return repository.save(entity);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public BigDecimal totalIngresos() {
        return repository.sumMontoByTipoAndTenantId(
                TipoMovimiento.INGRESO,
                TenantContext.getCurrentTenant());
    }

    @Override
    public BigDecimal totalEgresos() {
        return repository.sumMontoByTipoAndTenantId(
                TipoMovimiento.EGRESO,
                TenantContext.getCurrentTenant());
    }

    @Override
    public BigDecimal balance() {
        return totalIngresos().subtract(totalEgresos());
    }

    @Override
    @Transactional
    public ResumenMensualDTO cerrarMes() {
        YearMonth ahora = YearMonth.now();
        LocalDateTime desde = ahora.atDay(1).atStartOfDay();
        LocalDateTime hasta = ahora.atEndOfMonth().atTime(23, 59, 59);

        String tenantId = TenantContext.getCurrentTenant();
        BigDecimal ingresos = repository.sumMontoByTipoAndFechaBetweenAndTenantId(
                TipoMovimiento.INGRESO, desde, hasta, tenantId);
        BigDecimal egresos  = repository.sumMontoByTipoAndFechaBetweenAndTenantId(
                TipoMovimiento.EGRESO,  desde, hasta, tenantId);
        BigDecimal balanceMes = ingresos.subtract(egresos);

        // Guardar resumen en historial (upsert por mes/año/tenant)
        ResumenMensualEntity resumen = resumenRepository
                .findByMesAndAnioAndTenantId(ahora.getMonthValue(), ahora.getYear(), tenantId)
                .orElse(new ResumenMensualEntity());

        resumen.setMes(ahora.getMonthValue());
        resumen.setAnio(ahora.getYear());
        resumen.setTotalIngresos(ingresos);
        resumen.setTotalEgresos(egresos);
        resumen.setBalance(balanceMes);
        resumen.setFechaCierre(LocalDateTime.now());
        if (resumen.getTenantId() == null) {
            resumen.setTenantId(tenantId);
        }
        ResumenMensualEntity saved = resumenRepository.save(resumen);

        // Eliminar los movimientos de INGRESO del mes cerrado; los EGRESO se conservan
        repository.deleteByTipoAndFechaBetweenAndTenantId(
                TipoMovimiento.INGRESO, desde, hasta, tenantId);

        return toDTO(saved);
    }

    @Override
    public List<ResumenMensualDTO> historialMensual() {
        String tenantId = TenantContext.getCurrentTenant();
        return resumenRepository.findByTenantIdOrderByAnioDescMesDesc(tenantId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ------------------------------------------------------------------ helper

    private ResumenMensualDTO toDTO(ResumenMensualEntity e) {
        return new ResumenMensualDTO(
                e.getId(), e.getMes(), e.getAnio(),
                e.getTotalIngresos(), e.getTotalEgresos(),
                e.getBalance(), e.getFechaCierre());
    }
}
