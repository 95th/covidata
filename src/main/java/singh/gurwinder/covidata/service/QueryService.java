package singh.gurwinder.covidata.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import singh.gurwinder.covidata.dto.QueryInfo;
import singh.gurwinder.covidata.repo.ConfirmedStatRepo;
import singh.gurwinder.covidata.repo.TestingRepo;
import singh.gurwinder.covidata.repo.VaccinationRepo;

@Service
public class QueryService {
    private final ConfirmedStatRepo confirmedStatRepo;
    private final TestingRepo testingRepo;
    private final VaccinationRepo vaccinationRepo;

    public QueryService(ConfirmedStatRepo confirmedStatRepo, TestingRepo testingRepo, VaccinationRepo vaccinationRepo) {
        this.confirmedStatRepo = confirmedStatRepo;
        this.testingRepo = testingRepo;
        this.vaccinationRepo = vaccinationRepo;
    }

    public QueryInfo getByDate(LocalDate date) {
        var info = new QueryInfo();
        info.setConfirmed(confirmedStatRepo.findAllByDate(date));
        info.setTestings(testingRepo.findAllByDate(date));
        info.setVaccinations(vaccinationRepo.findAllByUpdatedOn(date));
        return info;
    }

    public QueryInfo getByState(String state) {
        var info = new QueryInfo();
        info.setConfirmed(confirmedStatRepo.findAllByState(state));
        info.setTestings(testingRepo.findAllByState(state));
        info.setVaccinations(vaccinationRepo.findAllByState(state));
        return info;
    }

    public QueryInfo getByStateAndDate(String state, LocalDate date) {
        var info = new QueryInfo();
        info.setConfirmed(confirmedStatRepo.findAllByStateAndDate(state, date));
        info.setTestings(testingRepo.findAllByStateAndDate(state, date));
        info.setVaccinations(vaccinationRepo.findAllByStateAndUpdatedOn(state, date));
        return info;
    }

    public QueryInfo getByStatesAndDate(List<String> state, LocalDate date) {
        var info = new QueryInfo();
        info.setConfirmed(confirmedStatRepo.findAllByStatesAndDate(state, date));
        info.setTestings(testingRepo.findAllByStatesAndDate(state, date));
        info.setVaccinations(vaccinationRepo.findAllByStatesAndUpdatedOn(state, date));
        return info;
    }
}
