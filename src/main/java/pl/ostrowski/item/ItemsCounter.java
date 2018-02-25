package pl.ostrowski.item;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ItemsCounter {

	@Getter
	private ItemInfo itemInfo;
	@Getter
	private int quantity;
	@Getter
	@Setter
	private BigDecimal totalPrice;
	
	public ItemsCounter(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
		this.quantity = 0;
		this.totalPrice = null;
	}
	
	public void addItems(int quantity) {
		if(quantity < 1) {
			return;
		}
		this.quantity += quantity;
	}
}