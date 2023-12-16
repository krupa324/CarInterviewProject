package com.example.carinterview.repository;

import com.example.carinterview.entities.CarEntityModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<CarEntityModel, Integer> {

    @Override
    List<CarEntityModel> findAll();

}
