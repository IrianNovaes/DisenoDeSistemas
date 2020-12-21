package ar.edu.davinci.dvds20202cg1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@PrimaryKeyJoinColumn(name = "vta_id")
@DiscriminatorValue("TARJETA")
@Table(name="ventas_tarjeta")


@Data
@NoArgsConstructor
@SuperBuilder
public class VentaTarjeta extends Venta implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4940343384573345479L;

    @Column(name = "vtt_cantidad_cuotas")
    private Integer cantidadCuotas;
    
    @Column(name = "vtt_coeficiente")
    private BigDecimal coeficienteTarjeta;

    public BigDecimal importeRecargado() {
        return super.importeBruto()
                .add(super.importeBruto().multiply(new BigDecimal(0.01)))
                        .add(coeficienteTarjeta
                                .multiply(new BigDecimal(cantidadCuotas)))
                                    .multiply(new BigDecimal(this.getItems().size()));
    }
    
    @Override
    public Double conRecargo(Double importeBase) {
        return importeBase + (importeBase * 0.01 + coeficienteTarjeta.doubleValue() * cantidadCuotas.doubleValue());
    }

}