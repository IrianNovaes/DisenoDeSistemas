package ar.edu.davinci.dvds20202cg1.controller.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.dvds20202cg1.controller.TiendaAppRest;
import ar.edu.davinci.dvds20202cg1.controller.rest.request.ItemInsertRequest;
import ar.edu.davinci.dvds20202cg1.controller.rest.request.ItemUpdateRequest;
import ar.edu.davinci.dvds20202cg1.controller.rest.response.VentaEfectivoResponse;
import ar.edu.davinci.dvds20202cg1.controller.rest.response.VentaResponse;
import ar.edu.davinci.dvds20202cg1.controller.rest.response.VentaTarjetaResponse;
import ar.edu.davinci.dvds20202cg1.model.Item;
import ar.edu.davinci.dvds20202cg1.model.Venta;
import ar.edu.davinci.dvds20202cg1.model.VentaEfectivo;
import ar.edu.davinci.dvds20202cg1.model.VentaTarjeta;
import ar.edu.davinci.dvds20202cg1.service.VentaService;
import ma.glasnost.orika.MapperFacade;

@RestController
public class VentaControllerRest extends TiendaAppRest{
    
    private final Logger LOGGER = LoggerFactory.getLogger(VentaControllerRest.class);

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private MapperFacade mapper;
    
    /**
     * Listar
     */
    @GetMapping(path = "/ventas/all")
    public List<Venta> getListAll() {
    LOGGER.info("listar todas las ventas");
    return ventaService.listAll();
    }
    

    /**
     * Listar paginado
     */
    @GetMapping(path = "/ventas")
    public ResponseEntity<Page<VentaResponse>> getList(Pageable pageable) {
        
        LOGGER.info("listar todas las ventas paginadas");
        LOGGER.info("Pageable: " + pageable);
        
        Page<VentaResponse> ventaResponse = null;
        Page<Venta> ventas = null;
        try {
            ventas = ventaService.list(pageable);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }  
           
        try {
            ventaResponse = ventas.map(venta -> mapper.map(venta, VentaResponse.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        
        return new ResponseEntity<>(ventaResponse, HttpStatus.OK);
    }

    /**
    * Buscar venta por id
    * @param ventaId identificador del venta
    * @return retorna el venta
    */
    
	@GetMapping(path = "/ventas/{ventaId}")
	public ResponseEntity<VentaResponse> getVenta(@PathVariable Long ventaId) {
		LOGGER.info("lista al venta solicitado");
		VentaResponse ventaResponse = null;
		Optional<Venta> ventaOptional = null;
		Venta venta = null;
		try {
			ventaOptional = ventaService.findById(ventaId);
			if (ventaOptional.isPresent()) {
				venta = ventaOptional.get();
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			ventaResponse = mapper.map(venta, VentaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}

	/**
	* Graba una Venta
	* @param venta
	* @param ventaResponse
	* @return
	*/
	@PostMapping(path = "/ventas")
	private ResponseEntity<VentaResponse> grabarVenta(VentaEfectivo venta, VentaResponse ventaResponse) {
		// Grabar el nuevo Venta
		try {
			venta = ventaService.save(venta);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		} // Convertir Venta en VentaResponse
		try {
			ventaResponse = mapper.map(venta, VentaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}

	
	/**
	* Grabar un nuevo item a la venta
	**
	@param ventaId identificador de la venta
	* @param datosItem son los datos para un nuevo item de venta
	* @return un venta modificada
	*/
	@PostMapping(path = "/ventas/{ventaId}/items")
	public ResponseEntity<VentaResponse> createItem(@PathVariable("ventaId") long ventaId,
			@RequestBody ItemInsertRequest datosItem) {
		VentaResponse ventaResponse = null;
		Item item = null;
		try {
			item = mapper.map(datosItem, Item.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		Venta venta = null;
		try {
			venta = ventaService.addItem(ventaId, item);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		// Convertir Venta en VentaResponse
		try {
			if (venta instanceof VentaEfectivo) {
				ventaResponse = mapper.map((VentaEfectivo) venta, VentaResponse.class);
			} else if (venta instanceof VentaTarjeta) {
				ventaResponse = mapper.map((VentaTarjeta) venta, VentaResponse.class);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}
    
	/**
	 * Modifica un item de la venta
	 **
	 * @param ventaId   identificador de la venta
	 * @param itemId    identificador del item
	 * @param datosItem son los datos para modificar el item de venta
	 * @return un venta modificada
	 */
	@PutMapping(path = "/ventas/{ventaId}/items/{itemId}")
	public ResponseEntity<VentaResponse> modifyItem(@PathVariable("ventaId") long ventaId,
			@PathVariable("itemId") long itemId, @RequestBody ItemUpdateRequest datosItem) {
		VentaResponse ventaResponse = null;
		Item item = null;
		try {
			item = mapper.map(datosItem, Item.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		Venta venta = null;
		try {
			venta = ventaService.updateItem(ventaId, itemId, item);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		// Convertir Venta en VentaResponse
		try {
			if (venta instanceof VentaEfectivo) {
				ventaResponse = mapper.map((VentaEfectivo) venta, VentaResponse.class);
			} else if (venta instanceof VentaTarjeta) {
				ventaResponse = mapper.map((VentaTarjeta) venta, VentaResponse.class);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}

	/**
	* Borrado del item de la venta
	* @param ventaId identificador de una venta
	* @param itemId identificador del item
	* @return
	*/
	@DeleteMapping("/ventas/{ventaId}/items/{itemId}")
	public ResponseEntity<VentaResponse> deleteCliente(@PathVariable("ventaId") long ventaId,
			@PathVariable("itemId") long itemId) {
		VentaResponse ventaResponse = null;
		Venta venta = null;
		try {
			venta = ventaService.deleteItem(ventaId, itemId);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		// Convertir Venta en VentaResponse
		try {
			if (venta instanceof VentaEfectivo) {
				ventaResponse = mapper.map((VentaEfectivo) venta, VentaResponse.class);
			} else if (venta instanceof VentaTarjeta) {
				ventaResponse = mapper.map((VentaTarjeta) venta, VentaResponse.class);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);

	}
    

}