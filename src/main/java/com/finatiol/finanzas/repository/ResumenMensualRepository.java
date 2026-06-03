package com.finatiol.finanzas.repository;

import com.finatiol.finanzas.entity.ResumenMensualEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumenMensualRepository extends JpaRepository<ResumenMensualEntity, Long> {

    List<ResumenMensualEntity> findByTenantIdOrderByAnioDescMesDesc(String tenantId);

    Optional<ResumenMensualEntity> findByMesAndAnioAndTenantId(Integer mes, Integer anio, String tenantId);
}
