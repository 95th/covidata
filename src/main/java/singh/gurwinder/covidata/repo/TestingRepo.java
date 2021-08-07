package singh.gurwinder.covidata.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import singh.gurwinder.covidata.entity.Testing;

public interface TestingRepo extends JpaRepository<Testing, Integer> {
    public List<Testing> findAllByDate(LocalDate date);

    public List<Testing> findAllByState(String state);

    public List<Testing> findAllByStateAndDate(String state, LocalDate date);

    @Query("select t from Testing t where t.state in (:states) and t.date = :date")
    public List<Testing> findAllByStatesAndDate(List<String> states, LocalDate date);
}
