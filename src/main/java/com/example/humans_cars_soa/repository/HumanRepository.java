package com.example.humans_cars_soa.repository;

import com.example.humans_cars_soa.model.Car;
import com.example.humans_cars_soa.model.Human;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface HumanRepository extends CrudRepository<Human, Long>, JpaRepository<Human, Long>, PagingAndSortingRepository<Human, Long> {
    List<Human> findByName(String name);


    @Query(value = "SELECT human.id, " +
            "human.name, " +
            "human.creationDate, " +
            "human.realHero, " +
            "human.hasToothpick, " +
            "human.impactSpeed, " +
            "human.soundtrackName, " +
            "human.minutesOfWaiting, " +
            "human.mood, " +
            "human.isDriver, " +
            "coordinate.id AS coordinate_id, " +
            "coordinate.x, " +
            "coordinate.y, " +
            "car.id AS car_id, " +
            "car.name AS car_name, " +
            "car.cool, " +
            "car.maxSeats " +
            "FROM Human human " +
            "LEFT JOIN Coordinate coordinate ON human.coordinate.id = coordinate.id " +
            "LEFT outer JOIN Car car ON human.car.id = car.id " +
            "WHERE human.id >= :id_min and human.id <= :id_max " +
            "AND (LOWER(human.name) like LOWER(:name)) " +
            "AND human.creationDate >= :creationDate_min and human.creationDate <= :creationDate_max " +
            "AND (:realHero IS NULL " +
            "OR human.realHero = :realHero " +
            ") " +
            "AND (:hasToothpick IS NULL " +
            "OR human.hasToothpick = :hasToothpick " +
            ") " +
            "AND human.impactSpeed >= :impactSpeed_min and human.impactSpeed <= :impactSpeed_max " +
            "AND (LOWER(human.soundtrackName) like LOWER(:soundtrackName)) " +
            "AND human.minutesOfWaiting >= :minutesOfWaiting_min and human.minutesOfWaiting <= :minutesOfWaiting_max " +
            "AND (LOWER(human.mood) like LOWER(:mood)  or :real_mood is null) " +
            "AND coordinate.id >= :coordinateId_min and coordinate.id <= :coordinateId_max " +
            "AND coordinate.x >= :x_min and coordinate.x <= :x_max " +
            "AND coordinate.y >= :y_min and coordinate.y <= :y_max " +
            "AND (car.id >= :carId_min and car.id <= :carId_max or :real_carId is null)" +
            "AND ((LOWER(car.name) like LOWER(:carName)) or :real_carName is null) " +
            "and (:carCool IS NULL " +
            "OR car.cool = :carCool " +
            ") " +
            "and ((:carMaxSeats_min <= car.maxSeats and car.maxSeats  <= :carMaxSeats_max)  or (:real_carMaxSeats_min is null and :real_carMaxSeats_max is null) ) " +
            "AND (:isDriver IS NULL " +
            "OR  human.isDriver = :isDriver " +
            ") " +
            ""
            , nativeQuery = false)
    Page<Object[]> findHumanFilter(Pageable pageable
            ,
                                   Long id_min,
                                   Long id_max,
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
                                   String real_mood,
                                   String mood,
                                   Long coordinateId_min,
                                   Long coordinateId_max,
                                   Integer x_min,
                                   Integer x_max,
                                   Integer y_min,
                                   Integer y_max,
                                   Long real_carId,
                                   Long carId_min,
                                   Long carId_max,
                                   String real_carName,
                                   String carName
            ,
                                   Boolean carCool,
                                   Integer real_carMaxSeats_min,
                                   Integer real_carMaxSeats_max,
                                   Integer carMaxSeats_min,
                                   Integer carMaxSeats_max
            ,
                                   Boolean isDriver
    );


    List<Human> findHumansByCarId(Long id);

    List<Human> findHumansByCoordinateId(Long id);

    List<Human> findHumansBySoundtrackName(String soundtrackName);


    List<Human> findHumansByImpactSpeedGreaterThan(Float amount);


    List<Human> findHumansByMinutesOfWaiting(Integer amount);
}
