package site.dealim.jobconsulting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCoverLetter {
    Long mclIdx;
    Long mclCclIdx; // 기업이 제공한 자소서 idx
    Long mclMemberIdx;
    String mclAnswer;
    String mclIsConfirm; // 미작성, 임시저장 , 저장
    String mclTitle;
    LocalDateTime mclRegistrationDate;
    LocalDateTime mclModifiedDate;
}
