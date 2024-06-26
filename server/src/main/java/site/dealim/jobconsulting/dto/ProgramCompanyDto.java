package site.dealim.jobconsulting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.dealim.jobconsulting.domain.Company;
import site.dealim.jobconsulting.domain.Program;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramCompanyDto {

    private Program program;
    private Company company;

}
