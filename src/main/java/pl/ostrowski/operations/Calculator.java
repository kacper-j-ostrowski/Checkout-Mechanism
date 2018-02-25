package pl.ostrowski.operations;

import java.math.BigDecimal;
import java.util.HashMap;

import pl.ostrowski.item.ItemsCounter;

public interface Calculator {

	public BigDecimal calculateTotalPrice(HashMap<Long, ItemsCounter> items) ;
	
}
