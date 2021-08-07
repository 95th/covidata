package singh.gurwinder.covidata.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import singh.gurwinder.covidata.dto.DataInfo;
import singh.gurwinder.covidata.repo.CasesRepo;
import singh.gurwinder.covidata.repo.TestingRepo;
import singh.gurwinder.covidata.repo.VaccinationRepo;

@Service
public class DataService {
    private final CasesRepo casesRepo;
    private final TestingRepo testingRepo;
    private final VaccinationRepo vaccinationRepo;

    public DataService(CasesRepo casesRepo, TestingRepo testingRepo, VaccinationRepo vaccinationRepo) {
        this.casesRepo = casesRepo;
        this.testingRepo = testingRepo;
        this.vaccinationRepo = vaccinationRepo;
    }

    public DataInfo getByDate(LocalDate date) {
        var info = new DataInfo();
        info.setCases(casesRepo.findAllByDate(date));
        info.setTestings(testingRepo.findAllByDate(date));
        info.setVaccinations(vaccinationRepo.findAllByUpdatedOn(date));
        return info;
    }

    public DataInfo getByState(String state) {
        var info = new DataInfo();
        info.setCases(casesRepo.findAllByStateOrderByDateAsc(state));
        info.setTestings(testingRepo.findAllByStateOrderByDateAsc(state));
        info.setVaccinations(vaccinationRepo.findAllByStateOrderByDateAsc(state));
        return info;
    }

    public DataInfo getByStateAndDate(String state, LocalDate date) {
        var info = new DataInfo();
        info.setCases(casesRepo.findAllByStateAndDate(state, date));
        info.setTestings(testingRepo.findAllByStateAndDate(state, date));
        info.setVaccinations(vaccinationRepo.findAllByStateAndUpdatedOn(state, date));
        return info;
    }

    public DataInfo getByStatesAndDate(List<String> state, LocalDate date) {
        var info = new DataInfo();
        info.setCases(casesRepo.findAllByStatesAndDate(state, date));
        info.setTestings(testingRepo.findAllByStatesAndDate(state, date));
        info.setVaccinations(vaccinationRepo.findAllByStatesAndUpdatedOn(state, date));
        return info;
    }
}
