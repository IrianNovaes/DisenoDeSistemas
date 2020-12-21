package ar.edu.davinci.dvds20202cg1.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prendas")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -302068569401023487L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "prd_id")
	private Long id;
	// private Estado estado;

	@Column(name = "prd_precio_base")
	private BigDecimal precioBase;

	@Column(name = "prd_tipo_prenda")
	@Enumerated(EnumType.STRING)
	private TipoPrenda tipo;

	@Column(name = "prd_descripcion")
	private String descripcion;

	public BigDecimal getPrecioFinal() {
		return precioBase; // estado.precioFinal(precioPropio);
	}
}