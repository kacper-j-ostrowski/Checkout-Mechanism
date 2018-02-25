package pl.ostrowski.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.ostrowski.entities.CombinedPromotion;

public interface CombinedPromotionsRepository extends CrudRepository<CombinedPromotion, Long> {
}
