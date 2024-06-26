package site.dealim.jobconsulting.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.dealim.jobconsulting.domain.Program;
import site.dealim.jobconsulting.dto.ProgramCompanyDto;
import site.dealim.jobconsulting.dto.WaitingRegDto;

import java.util.List;

@Mapper
public interface ProgramMapper {

    public int insertProgram(Program program);

    public String selectPgTitleByPgIdx(Long pgIdx);

    public int updateIsWithdrawn(Long pgIdx);

    public Program selectByPgIdx(Long pgIdx);

    public int updateProgram(Program program);

    public List<Program> selectAllPrograms();

    public List<Program> selectAllPgsInComIdx(Long comIdx);

    public List<Program> selectAllValidPrograms();

    public ProgramCompanyDto pgInfo(@Param("pgIdx") Long pgIdx);

    public int updatePgStatus(@Param("pgIdx") Long pgIdx, @Param("pgStatus") String pgStatus);

    public WaitingRegDto selectWaitingRegDto(Long pgIdx);
}
