package singh.gurwinder.covidata.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import singh.gurwinder.covidata.entity.Cases;

public interface CasesRepo extends JpaRepository<Cases, Integer> {
    public List<Cases> findAllByDate(LocalDate date);

    public List<Cases> findAllByState(String state);

    public List<Cases> findAllByStateAndDate(String state, LocalDate date);

    @Query("select t from Cases t where t.state in (:states) and t.date = :date")
    public List<Cases> findAllByStatesAndDate(List<String> states, LocalDate date);
}
