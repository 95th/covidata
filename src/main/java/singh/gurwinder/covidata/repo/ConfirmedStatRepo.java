package singh.gurwinder.covidata.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import singh.gurwinder.covidata.entity.ConfirmedStat;

public interface ConfirmedStatRepo extends JpaRepository<ConfirmedStat, Integer> {
    public List<ConfirmedStat> findAllByDate(LocalDate date);

    public List<ConfirmedStat> findAllByState(String state);

    public List<ConfirmedStat> findAllByStateAndDate(String state, LocalDate date);

    @Query("select t from ConfirmedStat t where t.state in (:states) and t.date = :date")
    public List<ConfirmedStat> findAllByStatesAndDate(List<String> states, LocalDate date);
}
