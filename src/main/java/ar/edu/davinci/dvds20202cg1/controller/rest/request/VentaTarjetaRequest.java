package ar.edu.davinci.dvds20202cg1.controller.rest.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaTarjetaRequest {
    
	private Long clienteId;
	
	private Integer cantidadCuotas;

}