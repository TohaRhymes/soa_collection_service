package com.example.humans_cars_soa.service;

import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.Car;
import org.springframework.data.domain.Page;


public interface CarService {
    public Page fetchAllCars(Integer page,
                             Integer size,
                             String sort,
                             String order,
                             Long id,
                             String name,
                             Boolean cool,
                             Integer maxSeats_min,
                             Integer maxSeats_max);

    public Car fetchCarById(Long id);

    public Car saveCar(Car car);

    public boolean deleteCarById(Long id);

    public boolean updateCarById(Long id, String name, Boolean cool, Integer maxSeats) throws ModelException;

}
