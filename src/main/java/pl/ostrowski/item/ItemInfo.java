package pl.ostrowski.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.ostrowski.entities.Item;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class ItemInfo {
	@Getter
	private long productCode;
	@Getter
	private String name;
	@Getter
	private BigDecimal price;

	public ItemInfo(Item item) {
        this(item.getProductCode(), item.getName(), item.getPrice());
	}
}