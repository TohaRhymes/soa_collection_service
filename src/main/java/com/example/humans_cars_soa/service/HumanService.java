package com.example.humans_cars_soa.service;

import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.Human;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;

public interface HumanService {
    public Page fetchAllHumans(Integer page,
                               Integer size,
                               String sort,
                               String order,
                               String name,
                               LocalDate creationDate_min,
                               LocalDate creationDate_max,
                               Boolean realHero,
                               Boolean hasToothpick,
                               Float impactSpeed_min,
                               Float impactSpeed_max,
                               String soundtrackName,
                               Integer minutesOfWaiting_min,
                               Integer minutesOfWaiting_max,
                               String mood,
                               Integer x_min,
                               Integer x_max,
                               Integer y_min,
                               Integer y_max,
                               String carName,
                               Boolean carCool,
                               Integer carMaxSeats_min,
                               Integer carMaxSeats_max,
                               Boolean isDriver);

    public Human fetchHumanById(Long id);

    public ArrayList<Human> fetchAllHumansByCarId(Long id);

    public ArrayList<Human> fetchHumansBySoundtrackName(String soundtrackName);

    public ArrayList<Human> fetchHumansByImpactSpeedGreaterThan(Float amount);

    public Human fetchHumansByMaxMinutesOfWaiting();

    public Human saveHuman(String name,
                           Boolean realHero,
                           Boolean hasToothpick,
                           Float impactSpeed,
                           String soundtrackName,
                           Integer minutesOfWaiting,
                           String mood,
                           Integer x,
                           Integer y,
                           Long carId,
                           Boolean isDriver) throws ModelException;

    public boolean updateHumanById(Long id,
                                   String name,
                                   Boolean realHero,
                                   Boolean hasToothpick,
                                   Float impactSpeed,
                                   String soundtrackName,
                                   Integer minutesOfWaiting,
                                   String mood,
                                   Integer x,
                                   Integer y,
                                   Long carId,
                                   Boolean isDriver) throws ModelException;

    public boolean deleteHumanById(Long id);


}
