package ar.edu.davinci.dvds20202cg1.controller.rest.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class VentaResponse {
    
	private Long id;
	private ClienteResponse cliente;
	private String fecha;
	private List<ItemResponse> items;
	private BigDecimal importeFinal;
}