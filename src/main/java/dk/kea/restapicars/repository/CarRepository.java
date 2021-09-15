package dk.kea.restapicars.repository;

import dk.kea.restapicars.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
