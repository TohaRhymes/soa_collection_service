package com.example.humans_cars_soa.controller;


import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.Human;
import com.example.humans_cars_soa.service.HumanService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping(path = "/v1/collection_api/humans")
@Api(value = "/v1/collection_api/humans",
        tags = "Humans",
        description = "Everything about humans")
public class HumanController {
    private final HumanService humanService;

    public HumanController(HumanService humanService) {
        this.humanService = humanService;
    }

    @GetMapping(path = "",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Fetch all humans with filters, sorting and ordering",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Page> fetchAllHumans(@ApiParam(name = "page", required = false, example = "0") @RequestParam(value = "page", required = false) Integer page,
                                               @ApiParam(name = "size", required = false, example = "5") @RequestParam(value = "size", required = false) Integer size,
                                               @ApiParam(name = "sort", required = false, example = "name") @RequestParam(value = "sort", required = false) String sort,
                                               @ApiParam(name = "order", required = false, example = "asc", allowableValues = "asc, desc") @RequestParam(value = "order", required = false) String order,
                                               @ApiParam(name = "name", required = false) @RequestParam(name = "name", required = false) String name,
                                               @ApiParam(name = "creation_date_min", value = "The minimal date of query", required = false, example = "2022-8-31") @RequestParam(name = "creation_date_min", required = false) String s_creationDate_min,
                                               @ApiParam(name = "creation_date_max", value = "The maximum date of query", required = false, example = "2022-12-31") @RequestParam(name = "creation_date_max", required = false) String s_creationDate_max,
                                               @ApiParam(name = "real_hero", required = false) @RequestParam(name = "real_hero", required = false) Boolean realHero,
                                               @ApiParam(name = "has_toothpick", required = false) @RequestParam(name = "has_toothpick", required = false) Boolean hasToothpick,
                                               @ApiParam(name = "impact_speed_min", value = "The minimal speed", required = false) @RequestParam(name = "impact_speed_min", required = false) Float impactSpeed_min,
                                               @ApiParam(name = "impact_speed_max", value = "The maximum speed", required = false) @RequestParam(name = "impact_speed_max", required = false) Float impactSpeed_max,
                                               @ApiParam(name = "soundtrack_name", required = false) @RequestParam(name = "soundtrack_name", required = false) String soundtrackName,
                                               @ApiParam(name = "minutes_of_waiting_min", required = false) @RequestParam(name = "minutes_of_waiting_min", required = false) Integer minutesOfWaiting_min,
                                               @ApiParam(name = "minutes_of_waiting_max", required = false) @RequestParam(name = "minutes_of_waiting_max", required = false) Integer minutesOfWaiting_max,
                                               @ApiParam(name = "mood", required = false) @RequestParam(name = "mood", required = false) String mood,
                                               @ApiParam(name = "x_min", required = false) @RequestParam(name = "x_min", required = false) Integer x_min,
                                               @ApiParam(name = "x_max", required = false) @RequestParam(name = "x_max", required = false) Integer x_max,
                                               @ApiParam(name = "y_min", required = false) @RequestParam(name = "y_min", required = false) Integer y_min,
                                               @ApiParam(name = "y_max", required = false) @RequestParam(name = "y_max", required = false) Integer y_max,
                                               @ApiParam(name = "car_name", required = false) @RequestParam(value = "car_name", required = false) String carName,
                                               @ApiParam(name = "car_cool", required = false) @RequestParam(value = "car_cool", required = false) Boolean carCool,
                                               @ApiParam(name = "car_max_seats_min", required = false) @RequestParam(value = "car_max_seats_min", required = false) Integer carMaxSeats_min,
                                               @ApiParam(name = "car_max_seats_max", required = false) @RequestParam(value = "car_max_seats_max", required = false) Integer carMaxSeats_max,
                                               @ApiParam(name = "is_driver", required = false) @RequestParam(name = "is_driver", required = false) Boolean isDriver) {

        LocalDate creationDate_min = null;
        if (s_creationDate_min != null) {
            creationDate_min = LocalDate.parse(s_creationDate_min);
        }
        LocalDate creationDate_max = null;
        if (s_creationDate_max != null) {
            creationDate_max = LocalDate.parse(s_creationDate_max);
        }
        return new ResponseEntity<>(humanService.fetchAllHumans(page,
                size,
                sort,
                order,
                name,
                creationDate_min,
                creationDate_max,
                realHero,
                hasToothpick,
                impactSpeed_min,
                impactSpeed_max,
                soundtrackName,
                minutesOfWaiting_min,
                minutesOfWaiting_max,
                mood,
                x_min,
                x_max,
                y_min,
                y_max,
                carName,
                carCool,
                carMaxSeats_min,
                carMaxSeats_max,
                isDriver), HttpStatus.OK);
    }


    @PostMapping(path = "",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Produce new human.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Human> addHuman(@ApiParam(name = "name", required = true) @RequestParam(name = "name", required = true) String name,
                                          @ApiParam(name = "real_hero", required = true) @RequestParam(name = "real_hero", required = true) Boolean realHero,
                                          @ApiParam(name = "has_toothpick", required = false) @RequestParam(name = "has_toothpick", required = false) Boolean hasToothpick,
                                          @ApiParam(name = "impact_speed", required = true) @RequestParam(name = "impact_speed", required = true) Float impactSpeed,
                                          @ApiParam(name = "soundtrack_name", required = true) @RequestParam(name = "soundtrack_name", required = true) String soundtrackName,
                                          @ApiParam(name = "minutes_of_waiting", required = true) @RequestParam(name = "minutes_of_waiting", required = true) Integer minutesOfWaiting,
                                          @ApiParam(name = "mood", required = false) @RequestParam(name = "mood", required = false) String mood,
                                          @ApiParam(name = "x", required = true) @RequestParam(name = "x", required = true) Integer x,
                                          @ApiParam(name = "y", required = true) @RequestParam(name = "y", required = true) Integer y,
                                          @ApiParam(name = "car_id", required = false) @RequestParam(name = "car_id", required = false) Long carId,
                                          @ApiParam(name = "is_driver", required = false) @RequestParam(name = "is_driver", required = false) Boolean isDriver) throws ModelException {
        try {
            Human human = humanService.saveHuman(name, realHero, hasToothpick, impactSpeed,
                    soundtrackName, minutesOfWaiting, mood, x, y, carId, isDriver);
            return new ResponseEntity<>(human, HttpStatus.OK);
        } catch (TransactionSystemException e) {
            System.out.println(e.getMessage());
            //Limits for x/y: 400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ModelException e) {
            System.out.println(e.getMessage());
            // Server error: something happened        500
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        //Server error        500
    }


    @PutMapping(path = "/{human-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Change human.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added"),
            @ApiResponse(code = 400, message = "Error formating: (check values requirements and restrictions)."),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public HttpStatus updateHuman(@ApiParam("human-id") @PathVariable(name = "human-id") Long id,
                                  @ApiParam(name = "name", required = false) @RequestParam(name = "name", required = false) String name,
                                  @ApiParam(name = "real_hero", required = false) @RequestParam(name = "real_hero", required = false) Boolean realHero,
                                  @ApiParam(name = "has_toothpick", required = false) @RequestParam(name = "has_toothpick", required = false) Boolean hasToothpick,
                                  @ApiParam(name = "impact_speed", required = false) @RequestParam(name = "impact_speed", required = false) Float impactSpeed,
                                  @ApiParam(name = "soundtrack_name", required = false) @RequestParam(name = "soundtrack_name", required = false) String soundtrackName,
                                  @ApiParam(name = "minutes_of_waiting", required = false) @RequestParam(name = "minutes_of_waiting", required = false) Integer minutesOfWaiting,
                                  @ApiParam(name = "mood", required = false) @RequestParam(name = "mood", required = false) String mood,
                                  @ApiParam(name = "x", required = false) @RequestParam(name = "x", required = false) Integer x,
                                  @ApiParam(name = "y", required = false) @RequestParam(name = "y", required = false) Integer y,
                                  @ApiParam(name = "car_id", required = false) @RequestParam(name = "car_id", required = false) Long carId,
                                  @ApiParam(name = "is_driver", required = false) @RequestParam(name = "is_driver", required = false) Boolean isDriver
    ) throws ModelException {
//        System.out.println("LMAO");
        try {
            if (humanService.updateHumanById(id, name, realHero, hasToothpick, impactSpeed,
                    soundtrackName, minutesOfWaiting, mood, x, y, carId, isDriver)) {
                return HttpStatus.OK;
            } else {
                // Server error: something happened        500
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (ModelException | TransactionSystemException e) {
            // ID not found (400)
            return HttpStatus.BAD_REQUEST;
        }
    }


    @GetMapping(path = "/{human-id}/mood/{mood}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Change human.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "Id not found or Error formating: (check values requirements and restrictions)."),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public HttpStatus updateHumanMood(@ApiParam("human-id") @PathVariable(name = "human-id", required = true) Long id,
                                      @ApiParam("mood") @PathVariable(name = "mood", required = true) String mood
    ) throws ModelException {
        try {
            if (humanService.updateHumanById(id, null, null, null, null, null, null, mood, null, null, null, null)) {
                return HttpStatus.OK;
            } else {
                // Server error: something happened        500
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (ModelException | TransactionSystemException e) {
            // ID not found (400)
            return HttpStatus.BAD_REQUEST;
        }
    }


    @GetMapping(path = "/{human-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Fetch human by id.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Id not found"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Human> fetchHumanById(@ApiParam("human-id") @PathVariable(name = "human-id") Long id) {
        Human human = humanService.fetchHumanById(id);
        if (human != null) {
            return new ResponseEntity<>(human, HttpStatus.OK);
        }
        // ID not found (400)
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // Server error: something happened        500
    }

    @DeleteMapping(path = "/{human-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Delete human by id",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Id not found"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public HttpStatus deleteHuman(@ApiParam("human-id") @PathVariable(name = "human-id") Long id) {
        if (humanService.deleteHumanById(id)) {
            return HttpStatus.OK;
        }
        // ID not found (400)
        return HttpStatus.NOT_FOUND;
        // Server error: something happened        500
    }


}
