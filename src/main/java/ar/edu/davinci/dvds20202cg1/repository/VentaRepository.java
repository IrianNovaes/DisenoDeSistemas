package ar.edu.davinci.dvds20202cg1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.dvds20202cg1.model.Venta;

	@Repository
	public interface VentaRepository extends JpaRepository<Venta, Long> {

	}
