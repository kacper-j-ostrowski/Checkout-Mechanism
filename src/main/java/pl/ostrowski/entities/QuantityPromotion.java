package pl.ostrowski.entities;

import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class QuantityPromotion {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int productCode;
	private int quantity;
	private BigDecimal specialPrice;
}
