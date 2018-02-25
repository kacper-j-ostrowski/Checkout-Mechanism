package pl.ostrowski;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.ostrowski.entities.CombinedPromotion;
import pl.ostrowski.entities.Item;
import pl.ostrowski.item.Basket;
import pl.ostrowski.item.ItemInfo;
import pl.ostrowski.operations.CalculatorFactory;
import pl.ostrowski.operations.PriceCalculator;
import pl.ostrowski.repositories.CombinedPromotionsRepository;
import pl.ostrowski.repositories.ItemRepository;
import pl.ostrowski.repositories.QuantityPromotionsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CombinedPromotionTests {

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
		CombinedPromotion cP1 = new CombinedPromotion();
		cP1.setFirstProductCode(1002);
		cP1.setSecondProductCode(1003);
		cP1.setNewPrice(new BigDecimal(3.00));
		combinedPromotionsRepository.save(cP1);
		
		CombinedPromotion cP2 = new CombinedPromotion();
		cP2.setFirstProductCode(1004);
		cP2.setSecondProductCode(1005);
		cP2.setNewPrice(new BigDecimal(2.00));
		combinedPromotionsRepository.save(cP2);
	}
	
	private void prepareItems() {
		Item item = new Item();
		item.setName("piwo");
		item.setPrice(new BigDecimal(3.99));
		item.setProductCode(1002);
		itemRepository.save(item);
		
		item = new Item();
		item.setName("chipsy");
		item.setPrice(new BigDecimal(2.50));
		item.setProductCode(1003);
		itemRepository.save(item);
		
		item = new Item();
		item.setName("sok");
		item.setPrice(new BigDecimal(2.59));
		item.setProductCode(1004);
		itemRepository.save(item);
		
		
		item = new Item();
		item.setName("woda");
		item.setPrice(new BigDecimal(0.79));
		item.setProductCode(1005);
		itemRepository.save(item);
	}

	private void addItemsToBasket() {
		for(int i=0; i<10; i++) {
			Item item = itemRepository.findOne(new Long(1002));
			ItemInfo itemInfo = new ItemInfo(item);
			basket.addItem(itemInfo);
		}
		
		for(int i=0; i<5; i++) {
			Item item = itemRepository.findOne(new Long(1003));
			ItemInfo itemInfo = new ItemInfo(item);
			basket.addItem(itemInfo);
		}	
		
		for(int i=0; i<100; i++) {
			Item item = itemRepository.findOne(new Long(1004));
			ItemInfo itemInfo = new ItemInfo(item);
			basket.addItem(itemInfo);
		}
		
		for(int i=0; i<39; i++) {
			Item item = itemRepository.findOne(new Long(1005));
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
		// 5*3 (15) + 5*3,99 (19,95) + 5*2,50 (12,5) + 39*2 (78) + 61*2,59 (157,99) + 39*0,79 (30,81) = 314,25
		assertTrue("Price not equal", basket.getTotalPrice().compareTo(new BigDecimal("314.25")) == 0);
	}

}