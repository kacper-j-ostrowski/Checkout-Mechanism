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
public class SeriesOfProductTest {

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
		
		item = new Item();
		item.setName("piwo");
		item.setPrice(new BigDecimal(3.65));
		item.setProductCode(2222);
		itemRepository.save(item);
		
		item = new Item();
		item.setName("bigos");
		item.setPrice(new BigDecimal(15.99));
		item.setProductCode(3333);
		itemRepository.save(item);
		
		
		item = new Item();
		item.setName("maslo");
		item.setPrice(new BigDecimal(6.10));
		item.setProductCode(4444);
		itemRepository.save(item);
		
		
		item = new Item();
		item.setName("kawa");
		item.setPrice(new BigDecimal(32.55));
		item.setProductCode(5555);
		itemRepository.save(item);
		
		
		item = new Item();
		item.setName("herbata");
		item.setPrice(new BigDecimal(4.44));
		item.setProductCode(6666);
		itemRepository.save(item);
	}

	private void addItemsToBasket() {
		int array[] = new int[] {1111,2222,3333,4444,5555,6666};
		for(int i=0; i<array.length;i++) {
			Item item = itemRepository.findOne(new Long(array[i]));
			ItemInfo itemInfo = new ItemInfo(item);
			basket.addItem(itemInfo);
		}
	}

	@Before
	public void prepareData() {
		prepareBasket();
		prepareItems();
		addItemsToBasket();
	}

	@Test
	public void contextLoads() {
		assertTrue("Price not equal", basket.getTotalPrice().compareTo(new BigDecimal("84.93")) == 0);
	}

}