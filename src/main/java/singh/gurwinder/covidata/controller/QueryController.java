package singh.gurwinder.covidata.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import singh.gurwinder.covidata.dto.QueryInfo;
import singh.gurwinder.covidata.service.QueryService;

@RestController
public class QueryController {
    private final QueryService service;

    public QueryController(QueryService service) {
        this.service = service;
    }

    @GetMapping(path = "Get_date_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryInfo findByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getByDate(date);
    }

    @GetMapping(path = "Get_state_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryInfo findByState(@RequestParam("state") String state) {
        return service.getByState(state);
    }

    @GetMapping(path = "Pinpoint_state", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryInfo findByStateAndDate(@RequestParam("state") String state,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getByStateAndDate(state, date);
    }

    @GetMapping(path = "Pinpoint_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueryInfo findByStateAndDate(@RequestParam("state") List<String> state,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getByStatesAndDate(state, date);
    }
}
