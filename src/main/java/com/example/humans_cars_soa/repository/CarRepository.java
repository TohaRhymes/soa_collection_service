package com.example.humans_cars_soa.repository;

import com.example.humans_cars_soa.model.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CarRepository extends CrudRepository<Car, Long>, JpaRepository<Car, Long>, PagingAndSortingRepository<Car, Long> {

    List<Car> findByName(String name);

    @Query("SELECT car.id, car.name, car.cool, car.maxSeats " +
            "FROM Car car " +
            "where (LOWER(car.name) like LOWER(:name))" +
            "and ( :cool IS NULL OR car.cool = :cool ) " +
            "and :maxSeats_min <= car.maxSeats and car.maxSeats <= :maxSeats_max")
    Stream<Object[]> findCarFilter(Pageable pageable, String name, Boolean cool, Integer maxSeats_min, Integer maxSeats_max);

}
