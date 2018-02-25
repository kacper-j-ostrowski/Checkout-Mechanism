package pl.ostrowski.operations;

import java.math.BigDecimal;
import java.util.HashMap;

import pl.ostrowski.entities.CombinedPromotion;
import pl.ostrowski.entities.QuantityPromotion;
import pl.ostrowski.item.ItemsCounter;
import pl.ostrowski.repositories.CombinedPromotionsRepository;
import pl.ostrowski.repositories.QuantityPromotionsRepository;

public class PriceCalculator implements Calculator {

	CombinedPromotionsRepository combinedPromotionsRepository;
    QuantityPromotionsRepository quantityPromotionsRepository;

	public PriceCalculator(CombinedPromotionsRepository combinedPromotionsRepository, QuantityPromotionsRepository quantityPromotionsRepository) {
		this.combinedPromotionsRepository = combinedPromotionsRepository;
		this.quantityPromotionsRepository = quantityPromotionsRepository;
	}

	public BigDecimal calculateTotalPrice(HashMap<Long, ItemsCounter> items) {

		if (items == null || items.size() == 0) {
			return new BigDecimal(0.0);
		}

		BigDecimal totalPrice = new BigDecimal(0.0);

		for (Long productCode : items.keySet()) {
			ItemsCounter item = items.get(productCode);
			BigDecimal priceForItem = calculatePriceForeItem(item, items);
			item.setTotalPrice(priceForItem);
			totalPrice = totalPrice.add(priceForItem);
		}

		return totalPrice;
	}

	private BigDecimal calculatePriceForeItem(ItemsCounter itemCounter, HashMap<Long, ItemsCounter> items) {

		long productCode = itemCounter.getItemInfo().getProductCode();
		BigDecimal price;

		CombinedPromotion combinedPromotion = combinedPromotionsRepository.findOne(productCode);
		QuantityPromotion quantityPromotion = quantityPromotionsRepository.findOne(productCode);

		if (combinedPromotion != null && quantityPromotion != null) {
			BigDecimal price1 = calculateCombinedPromotion(itemCounter, combinedPromotion, items);
			BigDecimal price2 = calculateQuantityPromotion(itemCounter, quantityPromotion);
			price = price1.compareTo(price2) < 0 ? price1 : price2;				
		} else if (combinedPromotion != null) {
			price = calculateCombinedPromotion(itemCounter, combinedPromotion, items);
		} else if (quantityPromotion != null) {
			price = calculateQuantityPromotion(itemCounter, quantityPromotion);			
		} else {
			price = calculateRegularPrice(itemCounter);
		}
		return price;
	}

	
	private BigDecimal calculateCombinedPromotion(ItemsCounter itemCounter, CombinedPromotion combinedPromotion, HashMap<Long, ItemsCounter> items) {
		BigDecimal price = null;
		ItemsCounter secondItem = items.get(combinedPromotion.getSecondProductCode());
		if (secondItem != null) {
			price = calculateCombinedPromotion(itemCounter, secondItem, combinedPromotion);			
		} else {
			price = calculateRegularPrice(itemCounter);
		}
		return price;
	}
	

	private BigDecimal calculateQuantityPromotion(ItemsCounter itemCounter, QuantityPromotion quantityPromotion) {
		if (itemCounter.getQuantity() >= quantityPromotion.getQuantity()) {
			int overQuantity = itemCounter.getQuantity() % quantityPromotion.getQuantity();
			int inQuantity = (int) Math.ceil(itemCounter.getQuantity() / quantityPromotion.getQuantity());

			BigDecimal normalPrice = itemCounter.getItemInfo().getPrice().multiply(new BigDecimal(overQuantity));
			BigDecimal discountedPrice = quantityPromotion.getSpecialPrice().multiply(new BigDecimal(inQuantity));
			BigDecimal cumulatedPrice = normalPrice.add(discountedPrice);

			return cumulatedPrice;
		}
		return itemCounter.getItemInfo().getPrice().multiply(new BigDecimal(itemCounter.getQuantity()));
	}


	private BigDecimal calculateCombinedPromotion(ItemsCounter firstItemCounter, ItemsCounter secondItemCounter,
			CombinedPromotion combinedPromotion) {

		int promotionQuantity = firstItemCounter.getQuantity() < secondItemCounter.getQuantity()
				? firstItemCounter.getQuantity()
				: secondItemCounter.getQuantity();
		int fI = (firstItemCounter.getQuantity() - promotionQuantity);

		BigDecimal promotionPrice = combinedPromotion.getNewPrice().multiply(new BigDecimal(promotionQuantity));
		promotionPrice = promotionPrice.add(firstItemCounter.getItemInfo().getPrice().multiply(new BigDecimal(fI)));
		return promotionPrice;
	}
	
	
	private BigDecimal calculateRegularPrice(ItemsCounter itemsCounter) {
		return itemsCounter.getItemInfo().getPrice().multiply(new BigDecimal(itemsCounter.getQuantity()));
	}
}