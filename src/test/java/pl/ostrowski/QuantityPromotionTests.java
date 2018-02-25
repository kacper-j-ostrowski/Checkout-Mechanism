package pl.ostrowski;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ostrowski.entities.Item;
import pl.ostrowski.entities.QuantityPromotion;
import pl.ostrowski.item.Basket;
import pl.ostrowski.item.ItemInfo;
import pl.ostrowski.operations.CalculatorFactory;
import pl.ostrowski.operations.PriceCalculator;
import pl.ostrowski.repositories.CombinedPromotionsRepository;
import pl.ostrowski.repositories.ItemRepository;
import pl.ostrowski.repositories.QuantityPromotionsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuantityPromotionTests {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CombinedPromotionsRepository combinedPromotionsRepository;

	@Autowired
	QuantityPromotionsRepository quantityPromotionsRepository;

	private Basket basket;

	private void prepareBasket() {
		basket = new Basket();
		PriceCalculator pc = (PriceCalculator) CalculatorFactory.getCalculatorInstance(CalculatorFactory.TYPE.PRICE,
				combinedPromotionsRepository, quantityPromotionsRepository);
		basket.setCalculationStrategy(pc);
	}
	
	private void preparePromotions() {
		QuantityPromotion qP1 = new QuantityPromotion();
		qP1.setProductCode(1000);
		qP1.setQuantity(5);
		qP1.setSpecialPrice(new BigDecimal(5.00));
		quantityPromotionsRepository.save(qP1);
		
		QuantityPromotion qP2 = new QuantityPromotion();
		qP2.setProductCode(1001);
		qP2.setQuantity(3);
		qP2.setSpecialPrice(new BigDecimal(12.00));
		quantityPromotionsRepository.save(qP2);
	}

	private void prepareItems() {
		Item item = new Item();
		item.setName("parowka");
		item.setPrice(new BigDecimal(2.99));
		item.setProductCode(1000);
		itemRepository.save(item);
		
		Item item1 = new Item();
		item1.setName("bigos");
		item1.setPrice(new BigDecimal(5.55));
		item1.setProductCode(1001);
		itemRepository.save(item);
	}

	private void addItemsToBasket() {

		for(int i=0; i<22;i++) {
			Item item = itemRepository.findOne(new Long(1000));
			ItemInfo itemInfo = new ItemInfo(item);
			basket.addItem(itemInfo);
		}
		
		for(int i=0; i<115;i++) {
			Item item = itemRepository.findOne(new Long(1001));
			ItemInfo itemInfo = new ItemInfo(item);
			basket.addItem(itemInfo);
		}
	}

	@Before
	public void prepareData() {
		preparePromotions();
		prepareBasket();
		prepareItems();
		addItemsToBasket();
	}

	@Test
	public void contextLoads() {		
		// 20.00 + 2*2.99 (5,98) + 38*12 (456) + 5,55 = 487,53
		assertTrue("Price not equal", basket.getTotalPrice().compareTo(new BigDecimal("487.53")) == 0);
	}

}