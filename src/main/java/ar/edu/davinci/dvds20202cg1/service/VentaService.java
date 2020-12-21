package ar.edu.davinci.dvds20202cg1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20202cg1.model.Item;
import ar.edu.davinci.dvds20202cg1.model.Venta;
import ar.edu.davinci.dvds20202cg1.model.VentaEfectivo;
import ar.edu.davinci.dvds20202cg1.model.VentaTarjeta;

public interface VentaService {
	
	public List<Venta> listAll();
	public Page<Venta> list(Pageable pageable);
	public Optional<Venta> findById(Long id);
	public VentaEfectivo save(VentaEfectivo venta) throws Exception;
	public VentaEfectivo save(VentaEfectivo ventaEfectivo, Item item) throws Exception;
	public VentaTarjeta save(VentaTarjeta venta) throws Exception;
	public VentaTarjeta save(VentaTarjeta ventaTarjeta, Item item) throws Exception;
	public void delete(Venta venta);
	public void delete(Long id);
	public long count();
	public Venta addItem(Long id, Item item) throws Exception;
	public Venta updateItem(Long ventaId, Long itemId, Item item) throws Exception;
	public Venta deleteItem(Long ventaId, Long itemId) throws Exception;
}
