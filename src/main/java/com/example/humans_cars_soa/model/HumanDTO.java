package com.example.humans_cars_soa.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class HumanDTO implements Serializable {
    /*
     * AUTO GENERATED ID
     * */
    @Id
    private Long id;

    private String name;
    private LocalDate creationDate;

    private Boolean realHero;

    private Boolean hasToothpick;

    private Float impactSpeed;

    private String soundtrackName;

    private Integer minutesOfWaiting;


    private Mood mood;


    private Long coordinateId;


    private Long carId;

    private Boolean isDriver;


    /*
     * BUILDER SETTERS
     * */
    public HumanDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public HumanDTO setName(String name) {
        this.name = name;
        return this;
    }

    public HumanDTO setCoordinateId(Long coordinateId) {
        this.coordinateId = coordinateId;
        return this;
    }

    public HumanDTO setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public HumanDTO setRealHero(Boolean realHero) {
        this.realHero = realHero;
        return this;
    }

    public HumanDTO setHasToothpick(Boolean hasToothpick) {

        this.hasToothpick = hasToothpick;
        return this;
    }

    public HumanDTO setImpactSpeed(Float impactSpeed) {
        this.impactSpeed = impactSpeed;
        return this;
    }

    public HumanDTO setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
        return this;
    }

    public HumanDTO setMinutesOfWaiting(Integer minutesOfWaiting) {
        this.minutesOfWaiting = minutesOfWaiting;
        return this;
    }

    public HumanDTO setMood(Mood mood) {
        this.mood = mood;
        return this;
    }

    public HumanDTO setCarId(Long carId) {
        this.carId = carId;
        return this;
    }

    public HumanDTO setIsDriver(Boolean isDriver) {
        this.isDriver = isDriver;
        return this;
    }


}
