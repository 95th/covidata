package singh.gurwinder.covidata.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "covid_vaccination", indexes = @Index(name = "idx1_covid_vaccination", columnList = "state, updatedOn"))
@Data
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate updatedOn;

    private String state;

    private int totalDosesAdministered;

    private int totalSessionsConducted;

    private int totalSites;

    private int firstDoseAdministered;

    private int secondDoseAdministered;

    private int malesVaccinated;

    private int femalesVaccinated;

    private int transgenderVaccinated;

    private int totalCovaxinAdministered;

    private int totalCoviShieldAdministered;

    private int totalSputnikVAdministered;

    private int aefi;

    private int age18To45;

    private int age45To60;

    private int ageOver60;

    private int totalVaccinated;
}
