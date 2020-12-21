package ar.edu.davinci.dvds20202cg1.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.slf4j.Logger;

import ar.edu.davinci.dvds20202cg1.model.Prenda;
import ar.edu.davinci.dvds20202cg1.model.TipoPrenda;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PrendaRepositoryTest {

	private final Logger LOGGER = LoggerFactory.getLogger(PrendaRepositoryTest.class);
	
	@Autowired
	private PrendaRepository prendaRepository;
	
	@Test
	void testFindAll() {
		assertNotNull(prendaRepository, "el repositorio es nulo");
		List<Prenda> prendas = prendaRepository.findAll();
		LOGGER.info("Prendas encontradas: " + prendas.size());
		
		assertNotNull(prendas, "la lista de prendas es nula");
		assertTrue(prendas.size() > 0, "no existen prendas");
	}

	@Test
	void testFindAllById() {
		
		Long id = 4L;
		Optional<Prenda> prendaOptional = prendaRepository.findById(id);
		if (prendaOptional.isPresent()) {
			Prenda prenda = prendaOptional.get();
			
			LOGGER.info("Prendas encontradas: " + prenda);
			assertEquals(TipoPrenda.PANTALON, prenda.getTipo());
		}
	}

}
