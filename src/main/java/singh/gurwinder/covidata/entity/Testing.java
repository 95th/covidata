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
@Table(name = "covid_testing", indexes = @Index(name = "idx1_covid_testing", columnList = "state, date"))
@Data
public class Testing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private String state;

    private int totalSamples;

    private int negative;

    private int positive;
}
