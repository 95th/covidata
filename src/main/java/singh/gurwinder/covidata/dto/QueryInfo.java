package singh.gurwinder.covidata.dto;

import java.util.List;

import lombok.Data;
import singh.gurwinder.covidata.entity.ConfirmedStat;
import singh.gurwinder.covidata.entity.Testing;
import singh.gurwinder.covidata.entity.Vaccination;

@Data
public class QueryInfo {
    private List<ConfirmedStat> confirmed;
    private List<Testing> testings;
    private List<Vaccination> vaccinations;
}
