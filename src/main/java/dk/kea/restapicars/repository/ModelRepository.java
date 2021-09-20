package dk.kea.restapicars.repository;

import dk.kea.restapicars.model.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepository extends CrudRepository<Model, Long> {
}
