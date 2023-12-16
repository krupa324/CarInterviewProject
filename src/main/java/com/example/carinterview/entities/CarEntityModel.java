package com.example.carinterview.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="car")
public class CarEntityModel {

    @Id
    @Column(name="carId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carId;

    @Column(name="carName")
    private String carName;

    @Column(name="model")
    private String carModel;

    @Column(name="Warranty")
    private int yearsOfWarranty;

}
