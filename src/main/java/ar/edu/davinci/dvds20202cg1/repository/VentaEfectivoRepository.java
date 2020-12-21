package ar.edu.davinci.dvds20202cg1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.dvds20202cg1.model.VentaEfectivo;

	@Repository
	public interface VentaEfectivoRepository extends JpaRepository<VentaEfectivo, Long> {

	}
