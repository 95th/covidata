package singh.gurwinder.covidata.dto;

import java.util.List;

import lombok.Data;
import singh.gurwinder.covidata.entity.Cases;
import singh.gurwinder.covidata.entity.Testing;
import singh.gurwinder.covidata.entity.Vaccination;

@Data
public class DataInfo {
    private List<Cases> cases;
    private List<Testing> testings;
    private List<Vaccination> vaccinations;
}
