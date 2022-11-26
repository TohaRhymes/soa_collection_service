package com.example.humans_cars_soa.service.Impl;

import com.example.humans_cars_soa.exception.ModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.example.humans_cars_soa.model.Coordinate;
import com.example.humans_cars_soa.repository.CoordinateRepository;
import com.example.humans_cars_soa.service.CoordinateService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.humans_cars_soa.utils.Utils.*;

@Slf4j
@Service
public class CoordinateServiceImpl implements CoordinateService {


    private final CoordinateRepository coordinateRepository;
    private final TransactionTemplate template;

    @Autowired
    public CoordinateServiceImpl(CoordinateRepository coordinateRepository, PlatformTransactionManager txManager) {
        this.coordinateRepository = coordinateRepository;
        this.template = new TransactionTemplate(txManager);
    }


    @Override
    @Transactional
    public Page fetchAllCoordinates(Integer page,
                                    Integer size,
                                    String sort,
                                    String order,
                                    Integer x_min,
                                    Integer x_max,
                                    Integer y_min,
                                    Integer y_max) {
        x_min = checkNull(x_min, Integer.MIN_VALUE);
        x_max = checkNull(x_max, Integer.MAX_VALUE);
        y_min = checkNull(y_min, Integer.MIN_VALUE);
        y_max = checkNull(y_max, Integer.MAX_VALUE);

        Pageable pageable = getPageable(page, size, sort, order);
        Page<Object[]> start = coordinateRepository.findCoordinateFilter(pageable, x_min, x_max, y_min, y_max);
        List<Coordinate> finish = new ArrayList<>();
        for (Object[] el : start) {
            Coordinate new_el = new Coordinate();
            new_el.setId((Long) el[0])
                    .setX((Integer) el[1])
                    .setY((Integer) el[2]);
            finish.add(new_el);
        }
        return new PageImpl<>(finish, pageable, start.getTotalElements());
    }

    @Override
    public Coordinate fetchCoordinateById(Long id) {
        return coordinateRepository.findById(id).orElse(null);
    }


    @Override
    public Coordinate saveCoordinate(Coordinate coordinate) {
        coordinateRepository.save(coordinate);
        return coordinate;
    }

    @Override
    public boolean deleteCoordinateById(Long id) {
        if (coordinateRepository.findById(id).isPresent()) {
            coordinateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCoordinateById(Long id, Integer x, Integer y) throws ModelException {
        Coordinate coordinate = this.fetchCoordinateById(id);
        if (coordinate == null)
            throw new ModelException("There's no such coordinate!");

        return Boolean.TRUE.equals(template.execute(transactionStatus -> {
            try {
                if (x != null)
                    coordinate.setX(x);
                if (y != null)
                    coordinate.setY(y);
                Coordinate newCoordinate = this.saveCoordinate(coordinate);
                log.info("Updated coordinate with id number {}", id);
                return true;
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                e.printStackTrace();
                log.info("Error while updating coordinate with id number {}", id);
            }
            return false;
        }));
    }


}
