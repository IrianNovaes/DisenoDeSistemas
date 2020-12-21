package ar.edu.davinci.dvds20202cg1.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import ar.edu.davinci.dvds20202cg1.model.Prenda;
import ar.edu.davinci.dvds20202cg1.model.TipoPrenda;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PrendaServiceImplTest {

	private final Logger LOGGER = LoggerFactory.getLogger(PrendaServiceImplTest.class);

	@Autowired
	private PrendaService prendaService;

	@Test
	void testListAll() {

		List<Prenda> prendas = prendaService.listAll();

		LOGGER.info("Prenda size:" + prendas.size());

		assertNotNull(prendas, "Prendas es nulo");
		assertTrue(prendas.size() > 0, "Prendas está vacio");

	}

	@Test
	void testList() {

		Pageable pageable = PageRequest.of(0, 2);
		Page<Prenda> prendas = prendaService.list(pageable);

		LOGGER.info("Prenda size:" + prendas.getSize());
		LOGGER.info("Prenda total page:" + prendas.getTotalPages());

		Pageable pageable1 = PageRequest.of(1, 2);
		Page<Prenda> prendas1 = prendaService.list(pageable1);

		LOGGER.info("Prenda size:" + prendas1.getSize());
		LOGGER.info("Prenda total page:" + prendas1.getTotalPages());

		assertNotNull(prendas, "Prendas es nulo");
		assertTrue(prendas.getSize() > 0, "Prendas está vacio");
	}

	@Test
	void testFindById() {

		Optional<Prenda> prendaOptional = prendaService.findById(4L);
		Prenda prenda = null;
		if (prendaOptional.isPresent()) {
			LOGGER.info("LA PRENDA FUE ENCONTRADA");
			prenda = prendaOptional.get();
		} else {
			LOGGER.info("LA PRENDA NO FUE ENCONTRADA");
		}

		assertNotNull(prenda);
		assertEquals(prenda.getDescripcion(), "Pantalon Gabardina Beige");
	}

	@Test
	void testFindById_withError() {
		Optional<Prenda> prendaOptional = prendaService.findById(0L);
		Prenda prenda = null;
		if (prendaOptional.isPresent()) {
			LOGGER.info("LA PRENDA FUE ENCONTRADA");
			prenda = prendaOptional.get();
		} else {
			LOGGER.info("LA PRENDA NO FUE ENCONTRADA");
		}

		assertNull(prenda);
	}

	@Test
	void testSave() {

		LOGGER.info("Prenda count antes insert: " + prendaService.count());
		Prenda prenda = Prenda.builder().descripcion("Tapado Negro").tipo(TipoPrenda.TAPADO)
				.precioBase(BigDecimal.valueOf(234.54D)).build();

		Prenda prendaCreada = prendaService.save(prenda);

		assertNotNull(prendaCreada);
		assertEquals(prenda.getDescripcion(), prendaCreada.getDescripcion());

		LOGGER.info("Prenda count después insert: " + prendaService.count());

	}

	@Test
	void testDelete() {

		LOGGER.info("Prenda count antes delete: " + prendaService.count());

		Optional<Prenda> prendaOptional = prendaService.findById(2L);
		Prenda prenda = null;
		if (prendaOptional.isPresent()) {
			LOGGER.info("LA PRENDA FUE ENCONTRADA");
			prenda = prendaOptional.get();
			prendaService.delete(prenda);
			LOGGER.info("Prenda count después delete: " + prendaService.count());
		} else {
			LOGGER.info("LA PRENDA NO FUE ENCONTRADA");
		}
		prendaService.delete(prenda);

		LOGGER.info("Prenda count después delete: " + prendaService.count());

	}

}