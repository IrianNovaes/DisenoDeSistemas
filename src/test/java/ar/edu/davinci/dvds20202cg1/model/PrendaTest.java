package ar.edu.davinci.dvds20202cg1.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PrendaTest {

	@Test
	void testBuilder() {
		
		//Given
		Long id = 1L;
		String camisa = "Camisa";
		TipoPrenda tipo = TipoPrenda.CAMISA;
		BigDecimal precio = new BigDecimal(10.2D);
		
		//When
		Prenda prenda = Prenda.builder()
				.id(id)
				.descripcion(camisa)
				.tipo(tipo)
				.precioBase(precio)
				.build();
		
		//Then		
		assertNotNull(prenda);
		assertEquals(id, prenda.getId());
		assertEquals(camisa, prenda.getDescripcion());
		assertEquals(tipo, prenda.getTipo());
		assertEquals(precio, prenda.getPrecioBase() );
	}

}
