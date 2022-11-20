package com.example.humans_cars_soa.service;

import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.Coordinate;

import java.util.ArrayList;

public interface CoordinateService {
    public ArrayList<Coordinate> fetchAllCoordinates(Integer page,
                                                     Integer size,
                                                     String sort,
                                                     String order,
                                                     Integer x_min,
                                                     Integer x_max,
                                                     Integer y_min,
                                                     Integer y_max) ;

    public Coordinate fetchCoordinateById(Long id);

    public Coordinate saveCoordinate(Coordinate coordinate);

    public boolean deleteCoordinateById(Long id);

    public boolean updateCoordinateById(Long id , Integer x, Integer y) throws ModelException;

}
