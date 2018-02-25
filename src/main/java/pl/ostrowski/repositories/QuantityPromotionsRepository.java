package pl.ostrowski.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.ostrowski.entities.QuantityPromotion;

public interface QuantityPromotionsRepository extends CrudRepository<QuantityPromotion, Long> {
}
