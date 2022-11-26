package com.example.humans_cars_soa.service.Impl;

import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.*;
import com.example.humans_cars_soa.service.CarService;
import com.example.humans_cars_soa.service.CoordinateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.humans_cars_soa.repository.HumanRepository;
import com.example.humans_cars_soa.service.HumanService;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.humans_cars_soa.utils.Utils.*;
import static com.example.humans_cars_soa.utils.Utils.checkNull;

@Slf4j
@Service
public class HumanServiceImpl implements HumanService {

    private final CoordinateService coordinateService;
    private final CarService carService;
    private final HumanRepository humanRepository;
    private final TransactionTemplate template;


    @Autowired
    public HumanServiceImpl(CoordinateService coordinateService, CarService carService, HumanRepository humanRepository, PlatformTransactionManager txManager) {
        this.coordinateService = coordinateService;
        this.carService = carService;
        this.humanRepository = humanRepository;
        this.template = new TransactionTemplate(txManager);
    }


    @Override
    @Transactional
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
                               Boolean isDriver) {
        name = checkNull(name, "");
        soundtrackName = checkNull(soundtrackName, "");
        String real_mood = mood;
        mood = checkNull(mood, "");
        x_min = checkNull(x_min, Integer.MIN_VALUE);
        x_max = checkNull(x_max, Integer.MAX_VALUE);
        y_min = checkNull(y_min, Integer.MIN_VALUE);
        y_max = checkNull(y_max, Integer.MAX_VALUE);
        String real_carName = carName;
        carName = checkNull(carName, "");
        creationDate_min = checkNull(creationDate_min, LocalDate.parse("0000-01-01"));
        creationDate_max = checkNull(creationDate_max, LocalDate.parse("3000-12-31"));
        impactSpeed_min = checkNull(impactSpeed_min, Float.MIN_VALUE);
        impactSpeed_max = checkNull(impactSpeed_max, Float.MAX_VALUE);
        minutesOfWaiting_min = checkNull(minutesOfWaiting_min, Integer.MIN_VALUE);
        minutesOfWaiting_max = checkNull(minutesOfWaiting_max, Integer.MAX_VALUE);
        Integer real_carMaxSeats_min = carMaxSeats_min;
        Integer real_carMaxSeats_max = carMaxSeats_max;
        carMaxSeats_min = checkNull(carMaxSeats_min, Integer.MIN_VALUE);
        carMaxSeats_max = checkNull(carMaxSeats_max, Integer.MAX_VALUE);
        Pageable pageable = getPageable(page, size, sort, order);
        Page<Object[]> start = humanRepository.findHumanFilter(pageable,
                "%" + name + "%",
                creationDate_min,
                creationDate_max,
                realHero,
                hasToothpick,
                impactSpeed_min,
                impactSpeed_max,
                "%" + soundtrackName + "%",
                minutesOfWaiting_min,
                minutesOfWaiting_max,
                real_mood,
                "%" + mood + "%",
                x_min,
                x_max,
                y_min,
                y_max,
                real_carName,
                "%" + carName + "%",
                carCool,
                real_carMaxSeats_min,
                real_carMaxSeats_max,
                carMaxSeats_min,
                carMaxSeats_max,
                isDriver);
        List<Human> finish = new ArrayList<>();
        for (Object[] el : start) {
            Human new_el = new Human();
            new_el.setId(((BigInteger) el[0]).longValue());
            if (el[1] != null) {
                new_el.setName((String) el[1]);
            }
            if (el[3] != null) {
                new_el.setRealHero((Boolean) el[3]);
            }
            if (el[4] != null) {
                new_el.setHasToothpick((Boolean) el[4]);
            }
            if (el[5] != null) {
                new_el.setImpactSpeed(((Float) el[5]));
            }
            if (el[6] != null) {
                new_el.setSoundtrackName((String) el[6]);
            }
            if (el[7] != null) {
                new_el.setMinutesOfWaiting((Integer) el[7]);
            }
            if (el[9] != null) {
                new_el.setIsDriver((Boolean) el[9]);
            }
            String new_mood = (String) el[8];
            if (new_mood != null) {
                switch (new_mood.toLowerCase().strip()) {
                    case ("sorrow") -> new_el.setMood(Mood.SORROW);
                    case ("apathy") -> new_el.setMood(Mood.APATHY);
                    case ("frenzy") -> new_el.setMood(Mood.FRENZY);
                }
            }

            Coordinate new_coord = new Coordinate();
            new_coord.setId(((BigInteger) el[10]).longValue())
                    .setX((Integer) el[11])
                    .setY((Integer) el[12]);
            new_el.setCoordinate(new_coord);

            Car new_car = new Car();
            if (el[13] != null) {
                new_car.setId(((BigInteger) el[13]).longValue())
                        .setName((String) el[14])
                        .setMaxSeats((Integer) el[16]);
                if (el[15] != null) {
                    new_car.setCool((Boolean) el[15]);
                }
                new_el.setCar(new_car);
            }

            finish.add(new_el);
        }
        return new PageImpl<>(finish, pageable, start.getTotalElements());
    }

    public ArrayList<Human> _fetchAllHumans(Integer page, Integer size, String sort, String order) {
        Pageable pageable = getPageable(page, size, sort, order);
        return new ArrayList<>(humanRepository.findAll(pageable).getContent());
    }

    @Override
    public Human fetchHumanById(Long id) {
        return humanRepository.findById(id).orElse(null);
    }

    @Override
    public ArrayList<Human> fetchAllHumansByCarId(Long id) {
        return new ArrayList<>(humanRepository.findHumansByCarId(id));
    }

    @Override
    public ArrayList<Human> fetchHumansBySoundtrackName(String soundtrackName) {
        return new ArrayList<>(humanRepository.findHumansBySoundtrackName(soundtrackName));
    }

    @Override
    public ArrayList<Human> fetchHumansByImpactSpeedGreaterThan(Float amount) {
        return new ArrayList<>(humanRepository.findHumansByImpactSpeedGreaterThan(amount));
    }

    @Override
    public Human fetchHumansByMaxMinutesOfWaiting() {
        ArrayList<Human> hbs = new ArrayList<>(humanRepository.findAll());
        Integer maxMinutes = -1;
        Integer curMinutes;
        Human maxMinutesHuman = null;
        for (Human hb : hbs) {
            curMinutes = hb.getMinutesOfWaiting();
            if (curMinutes != null && curMinutes > maxMinutes) {
                maxMinutes = curMinutes;
                maxMinutesHuman = hb;
            }
        }
        return maxMinutesHuman;
    }


    @Override
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
                           Boolean isDriver) throws ModelException {
        System.out.println(mood);
        System.out.println("FUCK1");
        Human human = new Human();

//        humanRepository.save(human);
        boolean flag = Boolean.TRUE.equals(template.execute(transactionStatus -> {
            try {
                human.setName(name)
                        .setRealHero(realHero)
                        .setHasToothpick(hasToothpick)
                        .setImpactSpeed(impactSpeed)
                        .setSoundtrackName(soundtrackName)
                        .setMinutesOfWaiting(minutesOfWaiting);
                if (mood != null) {
                    switch (mood.toLowerCase().strip()) {
                        case ("sorrow") -> human.setMood(Mood.SORROW);
                        case ("apathy") -> human.setMood(Mood.APATHY);
                        case ("frenzy") -> human.setMood(Mood.FRENZY);
                    }
                }


                Coordinate coordinate = coordinateService.saveCoordinate(new Coordinate().setX(x != null ? x : 0).setY(y != null ? y : 0));
                human.setCoordinate(coordinate);

                log.info("Cooords id: {}", coordinate.getId());

                Car car = null;
                if (carId != null) {
                    car = carService.fetchCarById(carId);
                }
                if (car == null)
                    car = carService.saveCar(new Car().setName("Zhiguli").setMaxSeats(5));
                log.info("Caar id: {}", car.getId());

                human.setCar(car);
                ArrayList<Human> hbs = this.fetchAllHumansByCarId(car.getId());
                Boolean _isDriver = isDriver == null ? Boolean.FALSE : isDriver;
                Boolean stolen = Boolean.FALSE;
                if (hbs.size() == 0) {
                    log.info("In - 1");
                    _isDriver = Boolean.TRUE;
                } else if (car.getMaxSeats().equals(hbs.size())) {
                    log.info("In - 2");
                    _isDriver = Boolean.TRUE;
                    stolen = Boolean.TRUE;
                }
                if (_isDriver.equals(Boolean.TRUE)) {
                    log.info("In - 3");
                    for (Human hb : hbs) {
                        if (hb.getIsDriver().equals(Boolean.TRUE) && stolen.equals(Boolean.TRUE)) {
                            hb.setCar(carService.saveCar(new Car().setName("Zhiguli").setMaxSeats(5)));
                            humanRepository.save(hb);
                        } else if (hb.getIsDriver().equals(Boolean.TRUE)) {
                            hb.setIsDriver(Boolean.FALSE);
                        }

                    }
                }
                human.setIsDriver(_isDriver);

                log.info("In - 4");
                humanRepository.save(human);
                log.info("Created hb with id number {}", human.getId());
                return true;
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                e.printStackTrace();
                log.info("Error while creating human with");
            }
            return false;
        }));

        if (flag) {
            return human;
        }
        throw new ModelException("Couldn't create HB, check data!");

    }

    @Override
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
                                   Boolean isDriver) throws ModelException {
        Human human = this.fetchHumanById(id);
        System.out.println(mood);
        System.out.println("FUCK1");
        if (human == null)
            throw new ModelException("There's no such human !");
        System.out.println(mood);
        System.out.println("FUCK2");

        return Boolean.TRUE.equals(template.execute(transactionStatus -> {
            try {
                if (name != null)
                    human.setName(name);
                if (realHero != null)
                    human.setRealHero(realHero);
                if (hasToothpick != null)
                    human.setHasToothpick(hasToothpick);
                if (impactSpeed != null)
                    human.setImpactSpeed(impactSpeed);
                if (soundtrackName != null)
                    human.setSoundtrackName(soundtrackName);
                if (minutesOfWaiting != null)
                    human.setMinutesOfWaiting(minutesOfWaiting);
                if (mood != null) {
                    System.out.println(mood);
                    System.out.println("FUCK");
                    switch (mood.toLowerCase().strip()) {
                        case ("sorrow") -> human.setMood(Mood.SORROW);
                        case ("apathy") -> human.setMood(Mood.APATHY);
                        case ("frenzy") -> human.setMood(Mood.FRENZY);
                    }
                }

                Coordinate coordinate = human.getCoordinate();
                coordinateService.updateCoordinateById(coordinate.getId(), x, y);


                Long previousCarId = human.getCar().getId();
                Boolean _isDriver = Boolean.FALSE;
                if (isDriver != null) {
                    _isDriver = isDriver;
                } else if (human.getIsDriver() != null) {
                    _isDriver = human.getIsDriver();
                }
                Boolean stolen = Boolean.FALSE;
                ArrayList<Human> hbs = null;
                if (carId != null && !previousCarId.equals(carId)) {
                    Car car = carService.fetchCarById(carId);
                    if (car != null) {
                        human.setCar(car);
                        previousCarId = car.getId();
                        hbs = this.fetchAllHumansByCarId(car.getId());
                        if (hbs.size() == 0) {
                            _isDriver = Boolean.TRUE;
                        } else if (car.getMaxSeats().equals(hbs.size())) {
                            _isDriver = Boolean.TRUE;
                            stolen = Boolean.TRUE;
                        }
                    }
                }
                if (previousCarId != null && _isDriver.equals(Boolean.TRUE)) {
                    if (hbs != null) {
                        for (Human hb : hbs) {
                            if (hb.getIsDriver().equals(Boolean.TRUE) && stolen.equals(Boolean.TRUE)) {
                                hb.setCar(carService.saveCar(new Car().setName("Zhiguli").setMaxSeats(5)));
                                humanRepository.save(hb);
                            } else if (hb.getIsDriver().equals(Boolean.TRUE)) {
                                hb.setIsDriver(Boolean.FALSE);
                            }
                        }
                    }
                }
                human.setIsDriver(_isDriver);

                humanRepository.save(human);
                log.info("Updated hb with id number {}", human.getId());
                return true;
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                e.printStackTrace();
                log.info("Error while updating coordinate with id number {}", id);
            }
            return false;
        }));
    }

    @Override
    public boolean deleteHumanById(Long id) {
        if (humanRepository.findById(id).isPresent()) {
            humanRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
