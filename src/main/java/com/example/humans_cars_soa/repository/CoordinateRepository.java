package com.example.humans_cars_soa.repository;

import com.example.humans_cars_soa.model.Car;
import com.example.humans_cars_soa.model.Coordinate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CoordinateRepository extends CrudRepository<Coordinate, Long>, JpaRepository<Coordinate, Long>, PagingAndSortingRepository<Coordinate, Long> {
    List<Coordinate> findByXAndY(Integer x, Integer y);

    @Query("SELECT coord.id, coord.x, coord.y FROM Coordinate coord WHERE coord.x >= :x_min and coord.x <= :x_max and coord.y >= :y_min and coord.y <= :y_max")
    Stream<Object[]> findCoordinateFilter(Pageable pageable, Integer x_min, Integer x_max, Integer y_min, Integer y_max);

}
