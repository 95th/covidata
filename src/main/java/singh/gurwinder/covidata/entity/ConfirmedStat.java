package singh.gurwinder.covidata.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "covid_confirmed_stat", indexes = @Index(name = "idx1_covid_confirmed_stat", columnList = "state, date"))
@Data
public class ConfirmedStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private LocalTime time;

    private String state;

    private int confirmedIndian;

    private int confirmedForeign;

    private int cured;

    private int deaths;

    private int confirmed;
}
