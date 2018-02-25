package pl.ostrowski.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.ostrowski.entities.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
}