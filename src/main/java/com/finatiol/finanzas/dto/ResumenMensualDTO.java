package com.finatiol.finanzas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ResumenMensualDTO {

    private Long id;
    private Integer mes;
    private Integer anio;
    private BigDecimal totalIngresos;
    private BigDecimal totalEgresos;
    private BigDecimal balance;
    private LocalDateTime fechaCierre;

    public ResumenMensualDTO() {}

    public ResumenMensualDTO(Long id, Integer mes, Integer anio,
                              BigDecimal totalIngresos, BigDecimal totalEgresos,
                              BigDecimal balance, LocalDateTime fechaCierre) {
        this.id = id;
        this.mes = mes;
        this.anio = anio;
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
        this.balance = balance;
        this.fechaCierre = fechaCierre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public BigDecimal getTotalIngresos() { return totalIngresos; }
    public void setTotalIngresos(BigDecimal totalIngresos) { this.totalIngresos = totalIngresos; }

    public BigDecimal getTotalEgresos() { return totalEgresos; }
    public void setTotalEgresos(BigDecimal totalEgresos) { this.totalEgresos = totalEgresos; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
}
