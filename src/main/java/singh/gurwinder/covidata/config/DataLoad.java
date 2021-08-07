package singh.gurwinder.covidata.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import singh.gurwinder.covidata.entity.Cases;
import singh.gurwinder.covidata.entity.Testing;
import singh.gurwinder.covidata.entity.Vaccination;
import singh.gurwinder.covidata.repo.CasesRepo;
import singh.gurwinder.covidata.repo.TestingRepo;
import singh.gurwinder.covidata.repo.VaccinationRepo;

@Component
public class DataLoad {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;
    private final DateTimeFormatter dateFormatDDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a");
    private final CasesRepo casesRepo;
    private final TestingRepo testingRepo;
    private final VaccinationRepo vaccinationRepo;

    @Value("${covid.datapath}")
    private String dataPath;

    @Autowired
    public DataLoad(CasesRepo casesRepo, TestingRepo testingRepo, VaccinationRepo vaccinationRepo) {
        this.casesRepo = casesRepo;
        this.testingRepo = testingRepo;
        this.vaccinationRepo = vaccinationRepo;
    }

    @PostConstruct
    public void loadData() throws IOException, CsvValidationException {
        loadFile("covid_19_india.csv", this::parseConfirmedStat, casesRepo::saveAll);
        loadFile("StatewiseTestingDetails.csv", this::parseTesting, testingRepo::saveAll);
        loadFile("covid_vaccine_statewise.csv", this::parseVaccination, vaccinationRepo::saveAll);
    }

    private <T> void loadFile(
            String fileName,
            Function<String[], T> parseFunction,
            Consumer<List<T>> saveFunction) throws IOException, CsvValidationException {
        try (var reader = Files.newBufferedReader(Paths.get(dataPath, fileName));
             var csv = new CSVReader(reader)) {
            // Skip the header
            csv.skip(1);

            var list = new ArrayList<T>();
            String[] out;

            while ((out = csv.readNext()) != null) {
                var c = parseFunction.apply(out);
                list.add(c);

                // Load in batch of 1000
                if (list.size() == 1000) {
                    saveFunction.accept(list);
                    list.clear();
                }
            }

            if (!list.isEmpty()) {
                saveFunction.accept(list);
            }
        }
    }

    private Cases parseConfirmedStat(String[] out) {
        var c = new Cases();
        c.setDate(LocalDate.parse(out[1], dateFormat));
        c.setTime(LocalTime.parse(out[2], timeFormat));
        c.setState(out[3]);
        c.setConfirmedIndian(parseInt(out[4]));
        c.setConfirmedForeign(parseInt(out[5]));
        c.setCured(parseInt(out[6]));
        c.setDeaths(parseInt(out[7]));
        c.setConfirmed(parseInt(out[8]));
        return c;
    }

    private Testing parseTesting(String[] out) {
        var c = new Testing();
        c.setDate(LocalDate.parse(out[0], dateFormat));
        c.setState(out[1]);
        c.setTotalSamples(parseInt(out[2]));
        c.setPositive(parseInt(out[3]));
        c.setNegative(parseInt(out[4]));
        return c;
    }

    private Vaccination parseVaccination(String[] out) {
        var c = new Vaccination();
        c.setUpdatedOn(LocalDate.parse(out[0], dateFormatDDMMYYYY));
        c.setState(out[1]);
        c.setTotalDosesAdministered(parseInt(out[2]));
        c.setTotalSessionsConducted(parseInt(out[3]));
        c.setTotalSites(parseInt(out[4]));
        c.setFirstDoseAdministered(parseInt(out[5]));
        c.setSecondDoseAdministered(parseInt(out[6]));
        c.setMalesVaccinated(parseInt(out[7]));
        c.setFemalesVaccinated(parseInt(out[8]));
        c.setTransgenderVaccinated(parseInt(out[9]));
        c.setTotalCovaxinAdministered(parseInt(out[10]));
        c.setTotalCoviShieldAdministered(parseInt(out[11]));
        c.setTotalSputnikVAdministered(parseInt(out[12]));
        c.setAefi(parseInt(out[13]));
        c.setAge18To45(parseInt(out[14]));
        c.setAge45To60(parseInt(out[15]));
        c.setAgeOver60(parseInt(out[16]));
        c.setTotalVaccinated(parseInt(out[17]));
        return c;
    }

    private int parseInt(String s) {
        if (s == null || s.isBlank() || s.equals("-")) {
            return 0;
        }

        if (s.indexOf('.') > -1) {
            return (int) Double.parseDouble(s);
        }

        return Integer.parseInt(s);
    }
}
