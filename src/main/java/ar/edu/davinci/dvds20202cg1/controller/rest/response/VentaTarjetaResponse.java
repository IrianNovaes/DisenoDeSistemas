package ar.edu.davinci.dvds20202cg1.controller.rest.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaTarjetaResponse extends VentaResponse{
    
	private Integer cantidadCuotas;
	private BigDecimal coeficienteTarjeta;
}