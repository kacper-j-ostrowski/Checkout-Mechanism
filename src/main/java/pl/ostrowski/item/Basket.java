package pl.ostrowski.item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.ostrowski.operations.Calculator;

@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Configurable
@Component
public class Basket {

	private HashMap<Long, ItemsCounter> items;
	private Calculator priceCalculator;
	private BigDecimal totalPrice;

	public Basket() {
		items = new HashMap<>();
		totalPrice = null;
	}

	public void addItem(ItemInfo item) {
		if (items.get(item.getProductCode()) == null) {
			items.put(item.getProductCode(), new ItemsCounter(item));
		}
		items.get(item.getProductCode()).addItems(1);
	}

	public BigDecimal getTotalPrice() {
		if (priceCalculator == null) {
			throw new RuntimeException("Lack of price calculator");
		}
		totalPrice = priceCalculator.calculateTotalPrice(items);
		return totalPrice;
	}

	public void setCalculationStrategy(Calculator priceCalculator) {
		this.priceCalculator = priceCalculator;
	}

	public boolean haveCalculationStrategy() {
	    return priceCalculator != null;
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Long, ItemsCounter> e : items.entrySet()) {
			sb.append("Code: " + e.getKey() + " | " + e.getValue().getItemInfo().getName() + " , Quantity: "
					+ e.getValue().getQuantity() + " || Price: " + e.getValue().getTotalPrice());
			sb.append("\n");
		}
		sb.append("TOTAL PRICE \n");
		sb.append(getTotalPrice());
		return sb.toString();
	}
}