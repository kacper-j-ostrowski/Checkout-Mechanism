package pl.ostrowski.operations;

import pl.ostrowski.repositories.CombinedPromotionsRepository;
import pl.ostrowski.repositories.QuantityPromotionsRepository;

public class CalculatorFactory {

	private CalculatorFactory() {}
	
	public static enum TYPE{
		PRICE
	}
	
	public static Calculator getCalculatorInstance(TYPE type, Object... args) {
		
		if(type.equals(TYPE.PRICE)) {
			return new PriceCalculator((CombinedPromotionsRepository) args[0], (QuantityPromotionsRepository) args[1]);
		}
		
		return null;
	}
	
}