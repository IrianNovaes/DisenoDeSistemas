package ar.edu.davinci.dvds20202cg1.controller.rest.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrendaInsertRequest {
    
    private BigDecimal precioBase;
    
    private String tipo;
    
    private String descripcion;

}