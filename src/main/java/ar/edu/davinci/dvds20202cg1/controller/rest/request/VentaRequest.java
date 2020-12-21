package ar.edu.davinci.dvds20202cg1.controller.rest.request;



import java.util.List;
import ar.edu.davinci.dvds20202cg1.model.Cliente;
import ar.edu.davinci.dvds20202cg1.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRequest {
    
	private Long id;
	private int cuotas;
	private Cliente cliente;
	private String fecha;
	private String tipo;
	private List<Item> items;


}