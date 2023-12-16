package com.example.carinterview.controller;

import com.example.carinterview.entities.CarContract;
import com.example.carinterview.entities.CarEntityModel;
import com.example.carinterview.repository.CarRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerTests {

    @Autowired
    CarRepository carRepository;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    private static CarEntityModel SEEDED_CAR_DATA;

    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();

        SEEDED_CAR_DATA = this.seedCarData();
    }

    @Test
    void getCars_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        List<CarContract> actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CarContract>>() {
        });

        assertEquals(1, actualResponse.size(), "Number of Records in the respoonse");
        assertCarControllerResponse(actualResponse.get(0));
    }

    @Test
    void getCarById_Success() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/cars/" + SEEDED_CAR_DATA.getCarId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        CarContract actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<CarContract>() {
        });
        assertCarControllerResponse(actualResponse);
    }

    @Test
    void getCarById_WhenNotFound_ThrowsNotFoundException() throws Exception {

        mockMvc.perform(get("/car/" + 100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveCar_Success() throws Exception {

        CarContract carContract = new CarContract();
        carContract.setCarId(10);
        carContract.setCarName("carName");
        carContract.setCarModel("carModel");
        carContract.setYearsOfWarranty(10);

        MvcResult mvcResult = mockMvc.perform(post("/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carContract)))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
        CarContract actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<CarContract>() {
        });
        assertEquals("carName", actualResponse.getCarName(), "carName");
        assertEquals("carModel", actualResponse.getCarModel(), "carModel");
        assertEquals(10, actualResponse.getYearsOfWarranty(), "yearsOfWarranty");
    }

    private void assertCarControllerResponse(CarContract carContract) {
        assertEquals(SEEDED_CAR_DATA.getCarId(), carContract.getCarId(), "carId");
        assertEquals(SEEDED_CAR_DATA.getCarName(), carContract.getCarName(), "carName");
        assertEquals(SEEDED_CAR_DATA.getCarModel(), carContract.getCarModel(), "carModel");
        assertEquals(SEEDED_CAR_DATA.getYearsOfWarranty(), carContract.getYearsOfWarranty(), "yearsOfWarranty");
    }

    private CarEntityModel seedCarData() {

        CarEntityModel carEntityModel = CarEntityModel.builder()
                .carId(1)
                .carName("Corolla")
                .carModel("Toyota")
                .yearsOfWarranty(10)
                .build();

        return carRepository.save(carEntityModel);
    }

}
