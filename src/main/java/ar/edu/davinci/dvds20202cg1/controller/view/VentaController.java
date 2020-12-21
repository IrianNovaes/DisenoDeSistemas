package ar.edu.davinci.dvds20202cg1.controller.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.davinci.dvds20202cg1.Constantes;
import ar.edu.davinci.dvds20202cg1.controller.TiendaApp;
import ar.edu.davinci.dvds20202cg1.controller.rest.request.VentaRequest;
import ar.edu.davinci.dvds20202cg1.controller.view.VentaController;
import ar.edu.davinci.dvds20202cg1.service.ClienteService;
import ar.edu.davinci.dvds20202cg1.service.ItemService;
import ar.edu.davinci.dvds20202cg1.service.PrendaService;
import ar.edu.davinci.dvds20202cg1.service.VentaService;
import ar.edu.davinci.dvds20202cg1.model.Venta;
import ar.edu.davinci.dvds20202cg1.model.VentaEfectivo;
import ar.edu.davinci.dvds20202cg1.model.VentaTarjeta;
import ar.edu.davinci.dvds20202cg1.model.Cliente;
import ar.edu.davinci.dvds20202cg1.model.Item;
import ar.edu.davinci.dvds20202cg1.model.Prenda;

@Controller
public class VentaController extends TiendaApp {
	private final Logger LOGGER = LoggerFactory.getLogger(VentaController.class);
	@Autowired
	private VentaService ventaService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PrendaService prendaService;

	@GetMapping(path = "ventas/list")
	public String showVentaPage(Model model) {
		
		LOGGER.info("GET - showVentaPage - /ventas/list");
		Pageable pageable = PageRequest.of(0, 20);
		Page<Venta> ventas = ventaService.list(pageable);
		LOGGER.info("GET - showVentaPage venta importe final: " + ventas.getContent().toString());
		model.addAttribute("listVentas", ventas);
		
		LOGGER.info("ventas.size: " + ventas.getNumberOfElements());
		
		return "ventas/list_ventas";
	}

	@GetMapping(path = "/ventas/new")
	public String showNewVentaPage(Model model) {
		LOGGER.info("GET - showNewVentaPage - /ventas/new");
		
		List<Cliente> clientes = clienteService.listAll();
		Venta venta = new VentaEfectivo();
		List<Item> items = itemService.listAll().stream()
		         								.filter(item -> item.getVenta().getId().equals(venta.getId())).collect(Collectors.toList());	
		
		model.addAttribute("venta", venta);
		model.addAttribute("items", items);
		model.addAttribute("clientes", clientes);
		
		LOGGER.info("ventas: " + venta.toString());
		LOGGER.info("items: " + items.toString());
		LOGGER.info("clientes: " + clientes.toString());
		
		return "ventas/new_ventas";
	}
	
	
//	@GetMapping(path = "/items/new/{id}")
//	public String showNewItemPage(@PathVariable(name = "id") Long ventaId, Model model ) {
//		LOGGER.info("GET - showNewItemPage - /item/new");
//		
//		Item item = new Item();
//		Optional<Venta> ventaOptional = ventaService.findById(ventaId);
//		item.setVenta(ventaOptional.get());
//		
//		         							
//		model.addAttribute("item", item);
//		model.addAttribute("prendas", prendaService.listAll());
//
//		LOGGER.info("items: " + item.toString());
//
//		return "ventas/new_item";
//	}
//	
	@GetMapping(path = "/items/new")
	public String showNewItemPage( Model model ) {
		LOGGER.info("GET - showNewItemPage - /item/new");
		
		Item item = new Item();
		         							
		model.addAttribute("item", item);
		model.addAttribute("prendas", prendaService.listAll());

		LOGGER.info("items: " + item.toString());

		return "ventas/new_item";
	}

	@PostMapping(value = "/items/save")
	public String saveItem(@ModelAttribute("item") Item item){
		LOGGER.info("POST - saveItem - /items/save");
		LOGGER.info("Item: " + item.toString());
		
		if (item.getCantidad() != 0)	
		{		
			itemService.save(item);
		}
		
		return "redirect:/tienda/ventas/new";
	}


	@PostMapping(value = "/ventas/save")
	public String saveVenta(@ModelAttribute("venta") VentaRequest ventaRequest) throws Exception{
		LOGGER.info("POST - saveVenta - /ventas/save");
		LOGGER.info("venta: " + ventaRequest.toString());
		
		String fechaStr = ventaRequest.getFecha();
		
		SimpleDateFormat formarter = new SimpleDateFormat(Constantes.FORMATO_FECHA);
		Date fecha = formarter.parse(fechaStr);
		
		if (ventaRequest.getTipo().equalsIgnoreCase("efectivo")) {
			VentaEfectivo venta = new VentaEfectivo();
			
			venta.setId(ventaRequest.getId());
			venta.setItems(ventaRequest.getItems());
			venta.setFecha(fecha);
			venta.setCliente(ventaRequest.getCliente());
			
			ventaService.save(venta);
		} else if (ventaRequest.getTipo().equalsIgnoreCase("tarjeta")) {
			VentaTarjeta venta = new VentaTarjeta();
			
			venta.setId(ventaRequest.getId());
			venta.setItems(ventaRequest.getItems());
			venta.setFecha(fecha);
			venta.setCliente(ventaRequest.getCliente());
			
			ventaService.save(venta);
		}
	
		return "redirect:/tienda/ventas/list";
	}

	@RequestMapping(value = "/ventas/edit/{id}", method = RequestMethod.GET)
	public ModelAndView showEditVentaPage(@PathVariable(name = "id") Long ventaId) {
		LOGGER.info("GET - showEditVentaPage - /ventas/edit/{id}");
		LOGGER.info("venta: " + ventaId);
		ModelAndView mav = new ModelAndView("ventas/edit_ventas");
		Optional<Venta> ventaOptional = ventaService.findById(ventaId);
		Venta venta = null;
		if (ventaOptional.isPresent()) {
			venta = ventaOptional.get();
			mav.addObject("venta", venta);
		}
		return mav;
	}

	@RequestMapping(value = "/ventas/delete/{id}", method = RequestMethod.GET)
	public String deleteVenta(@PathVariable(name = "id") Long ventaId) {
		LOGGER.info("GET - deleteVenta - /ventas/delete/{id}");
		LOGGER.info("venta: " + ventaId);
		ventaService.delete(ventaId);
		return "redirect:/tienda/ventas/list";
	}
}