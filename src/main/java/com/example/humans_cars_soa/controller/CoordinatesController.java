package com.example.humans_cars_soa.controller;


import com.example.humans_cars_soa.exception.ModelException;
import com.example.humans_cars_soa.model.Coordinate;
import io.swagger.annotations.*;

import com.example.humans_cars_soa.service.CoordinateService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/v1/collection_api/coordinates")
@Api(value = "/v1/collection_api/coordinates",
        tags = "Coordinates",
        description = "Everything about coordinates")
public class CoordinatesController {

    private final CoordinateService coordinateService;


    public CoordinatesController(CoordinateService coordinateService) {
        this.coordinateService = coordinateService;
    }


    @GetMapping(path = "",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Fetch All Coords with filters, sorting and ordering",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Page> fetchAllCoordinates(@ApiParam(name = "page", required = false, example = "0") @RequestParam(value = "page", required = false) Integer page,
                                                    @ApiParam(name = "size", required = false, example = "5") @RequestParam(value = "size", required = false) Integer size,
                                                    @ApiParam(name = "sort", required = false, example = "name") @RequestParam(value = "sort", required = false) String sort,
                                                    @ApiParam(name = "order", required = false, example = "asc", allowableValues = "asc, desc") @RequestParam(value = "order", required = false) String order,
                                                    @ApiParam(name = "x_min", required = false) @RequestParam(value = "x_min", required = false) Integer x_min,
                                                    @ApiParam(name = "x_max", required = false) @RequestParam(value = "x_max", required = false) Integer x_max,
                                                    @ApiParam(name = "y_min", required = false) @RequestParam(value = "y_min", required = false) Integer y_min,
                                                    @ApiParam(name = "y_max", required = false) @RequestParam(value = "y_max", required = false) Integer y_max) {
        return new ResponseEntity<>(coordinateService.fetchAllCoordinates(page,
                size,
                sort,
                order,
                x_min,
                x_max,
                y_min,
                y_max), HttpStatus.OK);
        // Server error: something happened        500
    }

    @PostMapping(path = "",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Produce new coordinate.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added"),
            @ApiResponse(code = 400, message = "Error formating: (check values requirements and restrictions)."),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Coordinate> addCoordinate(@ApiParam("x") @RequestParam(name = "x") Integer x,
                                                    @ApiParam("y") @RequestParam(name = "y") Integer y) {
        try {
            return new ResponseEntity<>(coordinateService.saveCoordinate(new Coordinate()
                    .setX(x)
                    .setY(y)), HttpStatus.OK
            );
        } catch (TransactionSystemException e) {
            //Limits for x/y: 500
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Server error        500
    }

    @PutMapping(path = "/{coordinate-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Change coordinate.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "Id not found or Error formating: (check values requirements and restrictions)."),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public HttpStatus updateCoordinate(@ApiParam("coordinate-id") @PathVariable(name = "coordinate-id") Long id,
                                       @ApiParam("x") @RequestParam(name = "x", required = false) Integer x,
                                       @ApiParam("y") @RequestParam(name = "y", required = false) Integer y) throws ModelException {
        try {
            if (coordinateService.updateCoordinateById(id, x, y)) {
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

    @GetMapping(path = "/{coordinate-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Fetch coordinate by id.",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Id not found"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Coordinate> fetchCoordinateById(@ApiParam("coordinate-id") @PathVariable(name = "coordinate-id") Long id) {
        Coordinate coordinate = coordinateService.fetchCoordinateById(id);
        if (coordinate != null) {
            return new ResponseEntity<>(coordinate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // Server error: something happened        500
    }

    @DeleteMapping(path = "/{coordinate-id}",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Delete coordinate by id",
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.ALL_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 404, message = "Id not found"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public HttpStatus deleteCoordinate(@ApiParam("coordinate-id") @PathVariable(name = "coordinate-id") Long id) {
        if (coordinateService.deleteCoordinateById(id)) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
        // Server error: something happened        500
    }


}
