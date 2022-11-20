package com.example.humans_cars_soa.controller;

import com.example.humans_cars_soa.model.Human;
import com.example.humans_cars_soa.service.HumanService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/functions",
        produces = MediaType.APPLICATION_XML_VALUE)
@Api(value = "/v1/functions",
        tags = "Functions",
        description = "Set of operations",
        produces = MediaType.APPLICATION_XML_VALUE)
public class FunctionsController {
    private final HumanService humanService;

    public FunctionsController(HumanService humanService) {
        this.humanService = humanService;
    }


    @GetMapping("/max_minutes_of_waiting")
    @ApiOperation(value = "Human with maxMinutesOfWaiting",
            notes = "Returns one (any) human whose minutesOfWaiting field value is the maximum value.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully completed"),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Human> maxMinutesOfWaiting() {
        Human human = humanService.fetchHumansByMaxMinutesOfWaiting();
        if (human != null) {
            return new ResponseEntity<>(human, HttpStatus.OK);
        }
        // Not found (404)
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // Server error: something happened        500
    }

    @GetMapping("/sound_amount/{sound-name}")
    @ApiOperation(value = "Number of humans with soundtrack",
            notes = "Returns the number of humans whose soundtrackName field value is equal to the given one.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully completed"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Integer> soundAmount(@ApiParam("sound-name") @PathVariable(name = "sound-name") String soundName) {
        return new ResponseEntity<>(humanService.fetchHumansBySoundtrackName(soundName).size(), HttpStatus.OK);
        // Server error: something happened        500
    }

    @GetMapping("/impact_speed_amount/{impact-speed}")
    @ApiOperation(value = "Number of humans with speed more than", notes = "Returns the number of humans whose impactSpeed field value is more than a given one.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully completed"),
            @ApiResponse(code = 500, message = "Internal server Error")
    })
    public ResponseEntity<Integer> soundAmount(@ApiParam("impact-speed") @PathVariable(name = "impact-speed") Float impactSpeed) {
        return new ResponseEntity<>(humanService.fetchHumansByImpactSpeedGreaterThan(impactSpeed).size(), HttpStatus.OK);
        // Server error: something happened        500
    }
}
