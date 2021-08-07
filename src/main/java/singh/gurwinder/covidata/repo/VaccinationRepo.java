package singh.gurwinder.covidata.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import singh.gurwinder.covidata.entity.Vaccination;

public interface VaccinationRepo extends JpaRepository<Vaccination, Integer> {
    public List<Vaccination> findAllByUpdatedOn(LocalDate date);

    public List<Vaccination> findAllByStateOrderByUpdatedOnAsc(String state);

    public List<Vaccination> findAllByStateAndUpdatedOn(String state, LocalDate date);

    @Query("select t from Vaccination t where t.state in (:states) and t.updatedOn = :date")
    public List<Vaccination> findAllByStatesAndUpdatedOn(List<String> states, LocalDate date);
}
