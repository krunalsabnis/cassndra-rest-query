package com.kru.iot.events;

import com.datastax.oss.driver.api.core.cql.Row;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kru.iot.repositories.EventEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
@Api(tags = "Events", consumes = "application/json")
public class EventsController {

    @Autowired
    private EventsService eventsService;


    @ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 200, message = "Ok")})
    @ApiOperation(value = "List all Events", notes = "List All Events. Accepts Pagination")
    @RequestMapping(method = RequestMethod.GET, value = "/events", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Slice<EventEntity>> getAllEvents(
            Pageable pageable) {
        Slice<EventEntity> result = eventsService.getEvents(pageable);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 200, message = "Ok")})
    @ApiOperation(value = "Events Query", notes = "Events Query")
    @RequestMapping(method = RequestMethod.POST, value = "/events", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Row> getAllEventsByQuery(@Validated  @RequestBody Query query) {
        Row result = eventsService.queryEvents(query);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
