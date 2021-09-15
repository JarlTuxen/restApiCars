package dk.kea.restapicars.controller;

import dk.kea.restapicars.model.Car;
import dk.kea.restapicars.repository.CarRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    private CarRepository carRepository;

    public CarController(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    //HTTP Get List
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> findAll(){
        List<Car> carList = new ArrayList<>();
        carRepository.findAll().forEach(carList::add);
        return ResponseEntity.status(HttpStatus.OK).body(carList);
    }

    //HTTP Get by ID
    public ResponseEntity<Optional<Car>> findById(@PathVariable Long id){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalCar);
        }
        else{
            //Not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(optionalCar);
        }
    }

    // HTTP Post i.e. create
    @CrossOrigin(origins = "*", exposedHeaders = "Location")
    @PostMapping(value = "/cars", consumes = "application/json")
    public ResponseEntity<Car> create(@RequestBody Car car){
        Car newCar = carRepository.save(car);
        //location /cars/{id} in responseheader
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("location", "/cars/" + newCar.getId());
        //return new ResponseEntity<Car>(newCar, headers, HttpStatus.CREATED);
        //ResponseEntity builder pattern
        return ResponseEntity.status(HttpStatus.CREATED).header("location", "/cars/" + newCar.getId()).body(newCar); //hvordan tilgå headers?
    }

    //HTTP Put i.e. update
    @PutMapping("/cars/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Car car){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (!optionalCar.isPresent()){
            // id not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{'msg' : 'car " + id + " not found'}");
        }
        carRepository.save(car);
        return ResponseEntity.status(HttpStatus.OK).body("{'msg' : 'updated'}");
    }

    //HTTP Delete
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (!optionalCar.isPresent()){
            //id not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{'msg' : 'car " + id + " not found'}");
        }
        carRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("{'msg' : 'deleted'}");
    }
}
