package com.finatiol.finanzas.service;

import com.finatiol.finanzas.dto.MovimientoRequestDTO;
import com.finatiol.finanzas.entity.MovimientoFinancieroEntity;
import com.finatiol.finanzas.entity.TipoMovimiento;
import com.finatiol.finanzas.repository.MovimientoFinancieroRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimientoFinancieroServiceImpl implements MovimientoFinancieroService {

    private final MovimientoFinancieroRepository repository;

    public MovimientoFinancieroServiceImpl(MovimientoFinancieroRepository repository) {
        this.repository = repository;
    }

    @Override
    public MovimientoFinancieroEntity registrar(MovimientoRequestDTO request) {
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
        return repository.sumMontoByTipo(TipoMovimiento.INGRESO);
    }

    @Override
    public BigDecimal totalEgresos() {
        return repository.sumMontoByTipo(TipoMovimiento.EGRESO);
    }

    @Override
    public BigDecimal balance() {
        return totalIngresos().subtract(totalEgresos());
    }
}
