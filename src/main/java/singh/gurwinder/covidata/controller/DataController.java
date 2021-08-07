package singh.gurwinder.covidata.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import singh.gurwinder.covidata.dto.DataInfo;
import singh.gurwinder.covidata.service.DataService;

@RestController
public class DataController {
    private final DataService service;

    public DataController(DataService service) {
        this.service = service;
    }

    @GetMapping(path = "Get_date_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataInfo findByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getByDate(date);
    }

    @GetMapping(path = "Get_state_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataInfo findByState(@RequestParam("state") String state) {
        return service.getByState(state);
    }

    @GetMapping(path = "Pinpoint_state", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataInfo findByStateAndDate(
            @RequestParam("state") String state,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getByStateAndDate(state, date);
    }

    @GetMapping(path = "Pinpoint_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataInfo findByStateAndDate(
            @RequestParam("state") List<String> state,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getByStatesAndDate(state, date);
    }
}
