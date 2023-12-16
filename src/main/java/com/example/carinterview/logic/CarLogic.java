package com.example.carinterview.logic;

import com.example.carinterview.entities.CarContract;
import com.example.carinterview.entities.CarEntityModel;
import com.example.carinterview.repository.CarRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CarLogic {

    private final CarRepository carRepository;

    public CarLogic(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarContract> getAllCars() {

        List<CarEntityModel> carEntityModels = this.carRepository.findAll();

        return carEntityModels.parallelStream().map(x -> mapCarEntityToCarContract(x)).collect(Collectors.toList());
    }

    public CarContract getCar(Integer carId) throws NotFoundException {

        Optional<CarEntityModel> carEntityModel = this.carRepository.findById(carId);

        if(carEntityModel !=null && carEntityModel.isPresent()) {
            return mapCarEntityToCarContract(carEntityModel.get());
        }
        throw new NotFoundException("Car Not Found for the provided carId");
    }

    public CarContract saveCar(CarContract carContract) {
        CarEntityModel carEntityModel = mapCarContractToCarEntity(carContract);

        return mapCarEntityToCarContract(this.carRepository.save(carEntityModel));
    }

    private CarContract mapCarEntityToCarContract(CarEntityModel carEntityModel) {

        CarContract carContract = new CarContract();
        carContract.setCarId(carEntityModel.getCarId());
        carContract.setCarName(carEntityModel.getCarName());
        carContract.setCarModel(carEntityModel.getCarModel());
        carContract.setYearsOfWarranty(carEntityModel.getYearsOfWarranty());

        return carContract;
    }

    private CarEntityModel mapCarContractToCarEntity(CarContract carContract) {

        return CarEntityModel.builder()
                .carId(carContract.getCarId())
                .carName(carContract.getCarName())
                .carModel(carContract.getCarModel())
                .yearsOfWarranty(carContract.getYearsOfWarranty())
                .build();
    }

}
