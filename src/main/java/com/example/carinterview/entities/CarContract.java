package com.example.carinterview.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarContract {

    private int carId;

    private String carName;

    private String carModel;

    private int yearsOfWarranty;
}
