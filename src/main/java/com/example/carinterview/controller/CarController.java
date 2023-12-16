package com.example.carinterview.controller;

import com.example.carinterview.entities.CarContract;
import com.example.carinterview.logic.CarLogic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    private final CarLogic carLogic;

    public CarController(CarLogic carLogic) {
        this.carLogic = carLogic;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarContract>>  getCars()
    {
        return ResponseEntity.ok(this.carLogic.getAllCars());
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarContract>  getCars(@PathVariable("id") Integer carId) throws Exception
    {
        return ResponseEntity.ok(this.carLogic.getCar(carId));
    }

    @PostMapping("/car")
    public ResponseEntity<CarContract> saveCar(@RequestBody CarContract carContract) {
        return ResponseEntity.ok(this.carLogic.saveCar(carContract));
    }
}
