package com.example.carinterview.logic;

import com.example.carinterview.entities.CarContract;
import com.example.carinterview.entities.CarEntityModel;
import com.example.carinterview.repository.CarRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarLogicTests {
    private final static int CAR_ID = 1;
    private final static String CAR_NAME = "carName";
    private final static String CAR_MODEL = "carModel";
    private final static int YEARS_OF_WARRANTY = 10;

    @Mock
    CarRepository carRepository;

    //As I have final property, not able to inject
//    @InjectMocks
//    @Autowired
    CarLogic carLogic;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        carLogic = new CarLogic(carRepository);
    }

    @Test
    void getAllCars_Success() {
        CarEntityModel expectedCarResponse = buildCarEntityModel();
        when(carRepository.findAll()).thenReturn(Collections.singletonList(expectedCarResponse));

        List<CarContract> carContractList = carLogic.getAllCars();

        assertEquals(1, carContractList.size(), "Number of cars in the response");
        assertCarContractAndEntity(carContractList.get(0), expectedCarResponse);
    }

    @Test
    void getCarByCarId_Success() throws Exception {
        int carId = 3;
        CarEntityModel expectedCarResponse = buildCarEntityModel();
        when(carRepository.findById(carId)).thenReturn(Optional.of(expectedCarResponse));

        CarContract carContract = carLogic.getCar(carId);

        assertCarContractAndEntity(carContract, expectedCarResponse);
    }

    @Test
    void getCarById_WhenNotFound_ThrowsNotFoundException() {
        int carId = 3;
        when(carRepository.findById(carId)).thenReturn(null);

        try {
            carLogic.getCar(carId);
            fail("NotFoundException was supposed to be thrown");
        }
        catch (NotFoundException ex) {
            assertEquals("Car Not Found for the provided carId", ex.getMessage());
        }
    }

    @Test
    void saveCar_Success() {
        CarContract carContract = buildCarContract();
        CarEntityModel carEntityModel = buildCarEntityModel();
        when(carRepository.save(ArgumentMatchers.any())).thenReturn(carEntityModel);

        carLogic.saveCar(carContract);

        ArgumentCaptor<CarEntityModel> carEntityModelArgumentCaptor = ArgumentCaptor.forClass(CarEntityModel.class);
        verify(carRepository, times(1)).save(carEntityModelArgumentCaptor.capture());

        CarEntityModel savedCarEntity = carEntityModelArgumentCaptor.getValue();

        assertEquals(carContract.getCarName(), savedCarEntity.getCarName(), "carName");
        assertEquals(carContract.getCarModel(), savedCarEntity.getCarModel(), "carModel");
        assertEquals(carContract.getYearsOfWarranty(), savedCarEntity.getYearsOfWarranty(), "yearsOfWarranty");
    }

    private static void assertCarContractAndEntity(CarContract carContract, CarEntityModel carEntityModel) {
        assertNotNull(carContract, "carContract == null");
        assertNotNull(carEntityModel, "carEntityModel == null");
        assertEquals(carContract.getCarId(), carEntityModel.getCarId(), "carId");
        assertEquals(carContract.getCarName(), carEntityModel.getCarName(), "carName");
        assertEquals(carContract.getCarModel(), carEntityModel.getCarModel(), "carModel");
        assertEquals(carContract.getYearsOfWarranty(), carEntityModel.getYearsOfWarranty(), "yearsOfWarranty");
    }

    private CarEntityModel buildCarEntityModel() {
        return CarEntityModel.builder()
                .carId(CAR_ID)
                .carName(CAR_NAME)
                .carModel(CAR_MODEL)
                .yearsOfWarranty(YEARS_OF_WARRANTY)
                .build();
    }

    private CarContract buildCarContract() {
        CarContract carContract = new CarContract();

        carContract.setCarId(CAR_ID);
        carContract.setCarName(CAR_NAME);
        carContract.setCarModel(CAR_MODEL);
        carContract.setYearsOfWarranty(YEARS_OF_WARRANTY);

        return carContract;
    }
}
