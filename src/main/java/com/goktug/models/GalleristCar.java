package com.goktug.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//!  One gallerist => Many Gallerist car
//! One car => Many gallerist car
//! (car_id = 1, gallerist_id= 1) != (car_id = 1, gallerist_id= 1) => To be unique

@Entity
@Table(name = "gallerist_car", uniqueConstraints = @UniqueConstraint(columnNames = { "gallerist_id",
        "car_id" }, name = "uq_gallerist_car"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalleristCar extends BaseEntity {

    @ManyToOne
    private Gallerist gallerist;

    @ManyToOne
    private Car car;
}
