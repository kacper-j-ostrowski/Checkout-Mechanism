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
import pl.ostrowski.item.Basket;
import pl.ostrowski.item.ItemInfo;
import pl.ostrowski.operations.CalculatorFactory;
import pl.ostrowski.operations.PriceCalculator;
import pl.ostrowski.repositories.CombinedPromotionsRepository;
import pl.ostrowski.repositories.ItemRepository;
import pl.ostrowski.repositories.QuantityPromotionsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckoutProjectApplicationTests {

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

	private void prepareItems() {
		Item item = new Item();
		item.setName("kielbasa");
		item.setPrice(new BigDecimal(22.2));
		item.setProductCode(1111);
		itemRepository.save(item);
	}

	private void addItemsToBasket() {
		Item item = itemRepository.findOne(new Long(1111));
		ItemInfo itemInfo = new ItemInfo(item);
		basket.addItem(itemInfo);
	}

	@Before
	public void prepareData() {
		prepareBasket();
		prepareItems();
		addItemsToBasket();
	}

	@Test
	public void contextLoads() {
		assertTrue("Price not equal", basket.getTotalPrice().compareTo(new BigDecimal("22.20")) == 0);
	}

}
