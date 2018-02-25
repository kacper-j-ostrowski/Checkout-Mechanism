package pl.ostrowski.checkout;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.ostrowski.entities.Item;
import pl.ostrowski.item.Basket;
import pl.ostrowski.item.ItemInfo;
import pl.ostrowski.operations.CalculatorFactory;
import pl.ostrowski.operations.PriceCalculator;

import pl.ostrowski.repositories.CombinedPromotionsRepository;
import pl.ostrowski.repositories.ItemRepository;
import pl.ostrowski.repositories.QuantityPromotionsRepository;

import java.math.BigDecimal;

@Component
@RestController
public class CheckoutMechanism {

	private static final String GET_ITEMS_IN_BASKET_URL = "/itemsInBasket";
	private static final String ADD_ITEM_TO_BASKET_URL = "/newItem";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Basket basket;

	@Autowired
    ItemRepository itemRepository;

    @Autowired
    CombinedPromotionsRepository combinedPromotionsRepository;

    @Autowired
    QuantityPromotionsRepository quantityPromotionsRepository;
	
   @RequestMapping(value = GET_ITEMS_IN_BASKET_URL, method = RequestMethod.GET)
    public String itemsInBasket(HttpServletRequest request) {
       logger.debug("Get basket");
       return basket.toString();
    }
   
   
   @RequestMapping(value = ADD_ITEM_TO_BASKET_URL, method = RequestMethod.POST, consumes = "application/json")
   public BigDecimal addItem(HttpServletRequest request, @RequestBody long productCode) {
       logger.debug("New item code to add: " + productCode);
       if(!basket.haveCalculationStrategy()) {
           PriceCalculator pc = (PriceCalculator) CalculatorFactory
                   .getCalculatorInstance(CalculatorFactory.TYPE.PRICE,
                           combinedPromotionsRepository, quantityPromotionsRepository);
           basket.setCalculationStrategy(pc);
       }
	   Item item = itemRepository.findOne(productCode);
	   ItemInfo itemInfo = new ItemInfo(item);
	   if(itemInfo != null) {
		   basket.addItem(itemInfo);
	   }
	   return basket.getTotalPrice();   
   }
}