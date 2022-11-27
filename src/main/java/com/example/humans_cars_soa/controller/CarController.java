package com.example.humans_cars_soa.controller;


import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.Car;
import com.example.humans_cars_soa.service.CarService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/v1/collection_api/cars")
@Api(value = "/v1/collection_api/cars",
        tags = "Cars",
        description = "CRUD for cars")
public class CarController {

    private final CarService carService;


    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(path = "",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "fetchAllCars with filters, sorting and ordering",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Page> fetchAllCars(@ApiParam(name = "page", required = false, example = "0") @RequestParam(value = "page", required = false) Integer page,
                                             @ApiParam(name = "size", required = false, example = "5") @RequestParam(value = "size", required = false) Integer size,
                                             @ApiParam(name = "sort", required = false, example = "name") @RequestParam(value = "sort", required = false) String sort,
                                             @ApiParam(name = "order", required = false, example = "asc", allowableValues = "asc, desc") @RequestParam(value = "order", required = false) String order,
                                             @ApiParam(name = "id", required = false) @RequestParam(name = "id", required = false) Long id,
                                             @ApiParam(name = "name", value = "part of the name", required = false, example = "Fer") @RequestParam(value = "name", required = false) String name,
                                             @ApiParam(name = "cool", required = false, type = "boolean", example = "false") @RequestParam(value = "cool", required = false) Boolean cool,
                                             @ApiParam(name = "max_seats_min", value = "Should be more than 0", required = false, example = "3") @RequestParam(value = "max_seats_min", required = false) Integer maxSeats_min,
                                             @ApiParam(name = "max_seats_max", value = "Should be more than 0", required = false, example = "5") @RequestParam(value = "max_seats_max", required = false) Integer maxSeats_max) {


        return new ResponseEntity<>(carService.fetchAllCars(page,
                size,
                sort,
                order,
                id,
                name,
                cool,
                maxSeats_min,
                maxSeats_max), HttpStatus.OK);
    }


//    @PostMapping(path = "",
//            produces = MediaType.APPLICATION_XML_VALUE,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ApiOperation(value = "Produce new car.",
//            produces = MediaType.APPLICATION_XML_VALUE,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully added"),
//            @ApiResponse(code = 400, message = "Error formatting"),
//            @ApiResponse(code = 500, message = "Internal server Error")
//    })
//    public ResponseEntity<Car> _addCar(@ApiParam(name = "name", example = "Ferrari", required = true) @RequestParam(name = "name", required = true) String name,
//                                      @ApiParam(name = "cool", required = false) @RequestParam(name = "cool", required = false) Boolean cool,
//                                      @ApiParam(name = "max_seats", required = true) @RequestParam(name = "max_seats", required = true) Integer maxSeats) {
//        try {
//            return new ResponseEntity<>(carService.saveCar(new Car()
//                    .setName(name)
//                    .setCool(cool)
//                    .setMaxSeats(maxSeats)), HttpStatus.OK
//            );
//        } catch (TransactionSystemException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping(path = "",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "Produce new car.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added"),
            @ApiResponse(code = 400, message = "Error formatting"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Car> addCar(@ApiParam(name = "car",  required = true) @RequestBody(required = true) Car car) {
        System.out.println("1111");
        try {
            return new ResponseEntity<>(carService.saveCar(car), HttpStatus.OK
            );
        } catch (TransactionSystemException e) {
            System.out.println();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{car-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "Change car.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "Error formatting or ID not found"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public HttpStatus updateCar(@ApiParam(name = "car-id", required = true) @PathVariable(name = "car-id", required = true) Long id,
                                @ApiParam("car") @RequestBody(required = false) Car car) throws ModelException {
        try {
            if (carService.updateCarById(id, car.getName(), car.getCool(), car.getMaxSeats())) {
                return HttpStatus.OK;
            } else {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } catch (ModelException | TransactionSystemException e) {
            // ID not found (400)
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping(path = "/{car-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Fetch car by id.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Id not found"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Car> fetchCarById(@ApiParam("car-id") @PathVariable(name = "car-id") Long id) {
        Car car = carService.fetchCarById(id);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{car-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Delete car by id",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Id not found"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public HttpStatus deleteCar(@ApiParam("car-id") @PathVariable(name = "car-id") Long id) {
        if (carService.deleteCarById(id)) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
        // Server error: something happened        500
    }


}
