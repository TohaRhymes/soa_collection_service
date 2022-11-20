package com.example.humans_cars_soa.service.Impl;

import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.Coordinate;
import com.example.humans_cars_soa.repository.CoordinateRepository;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.humans_cars_soa.model.Car;
import com.example.humans_cars_soa.repository.CarRepository;
import com.example.humans_cars_soa.service.CarService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.example.humans_cars_soa.utils.Utils.*;
import static com.example.humans_cars_soa.utils.Utils.checkNull;

@Slf4j
@Service
public class CarServiceImpl implements CarService {


    private final CarRepository carRepository;
    private final TransactionTemplate template;


    @Autowired
    public CarServiceImpl(CarRepository carRepository, PlatformTransactionManager txManager) {
        this.carRepository = carRepository;
        this.template = new TransactionTemplate(txManager);
    }


    public ArrayList<Car> _fetchAllCars(Integer page, Integer size, String sort, String order) {
        Pageable pageable = getPageable(page, size, sort, order);
        return new ArrayList<>(carRepository.findAll(pageable).getContent());
    }

    @Override
    @Transactional
    public ArrayList<Car> fetchAllCars(Integer page,
                                       Integer size,
                                       String sort,
                                       String order,
                                       String name,
                                       Boolean cool,
                                       Integer maxSeats_min,
                                       Integer maxSeats_max) {
        name = checkNull(name, "");
        maxSeats_min = checkNull(maxSeats_min, Integer.MIN_VALUE);
        maxSeats_max = checkNull(maxSeats_max, Integer.MAX_VALUE);

        Pageable pageable = getPageable(page, size, sort, order);
        List<Object[]> start = carRepository.findCarFilter(pageable, "%" + name + "%", cool, maxSeats_min, maxSeats_max).toList();
        List<Car> finish = new ArrayList<>();
        for (Object[] el : start) {
            Car new_el = new Car();
            new_el.setId((Long) el[0])
                    .setName((String) el[1])
                    .setMaxSeats((Integer) el[3]);
            if (el[2] != null) {
                new_el.setCool((Boolean) el[2]);
            }
            finish.add(new_el);
        }
        return new ArrayList<>(finish);
    }

    @Override
    public Car fetchCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Car saveCar(Car car) {
        carRepository.save(car);
        return car;
    }

    @Override
    public boolean deleteCarById(Long id) {
        if (carRepository.findById(id).isPresent()) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCarById(Long id, String name, Boolean cool, Integer maxSeats) throws ModelException {
        Car car = this.fetchCarById(id);
        if (car == null)
            throw new ModelException("There's no such car!");

        return Boolean.TRUE.equals(template.execute(transactionStatus -> {
            try {
                if (name != null)
                    car.setName(name);
                if (cool != null)
                    car.setCool(cool);
                if (maxSeats != null)
                    car.setMaxSeats(maxSeats);
                carRepository.save(car);
                log.info("Updated car with id number {}", id);
                return true;
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                e.printStackTrace();
                log.info("Error while updating car with id number {}", id);
            }
            return false;
        }));
    }


}