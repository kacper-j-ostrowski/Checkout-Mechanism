package pl.ostrowski.entities;

import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
public class Item {
	@Id
	private long productCode;
	private BigDecimal price;
	private String name;
}