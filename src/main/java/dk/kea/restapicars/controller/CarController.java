package dk.kea.restapicars.controller;

import dk.kea.restapicars.model.Car;
import dk.kea.restapicars.model.Model;
import dk.kea.restapicars.repository.CarRepository;
import dk.kea.restapicars.repository.ModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//alle /cars requests håndteres i denne controller
@RequestMapping("/cars")
@RestController
public class CarController {

    private CarRepository carRepository;
    private ModelRepository modelRepository;

    public CarController(CarRepository carRepository, ModelRepository modelRepository) {
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
    }

    //HTTP Get List
    @GetMapping("")
    public ResponseEntity<List<Car>> findAll(){
        List<Car> carList = new ArrayList<>();
        carRepository.findAll().forEach(carList::add);
        return ResponseEntity.status(HttpStatus.OK).body(carList);
    }

    //HTTP Get by ID
    @GetMapping("/{id}")
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
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Car> create(@RequestBody Car car){
        Car newCar = carRepository.save(car);

        //brug id fra newCar som fremmednøgle i model
        Set<Model> newModels = car.getModels();
        for (Model model : newModels){
            model.setCar(newCar);
            modelRepository.save(model);
        }

        //ResponseEntity builder pattern
        //location /cars/{id} in responseheader
        return ResponseEntity.status(HttpStatus.CREATED).header("location", "/cars/" + newCar.getId()).body(newCar);
    }

    //HTTP Put i.e. update
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Car car){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()){
            if (id.equals(car.getId())) {
                //path id og car object id ens så gem
                carRepository.save(car);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            else{
                //forskel på path id og car object id så Bad Request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        else{
            // id not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //HTTP Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (!optionalCar.isPresent()){
            //id not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        carRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
