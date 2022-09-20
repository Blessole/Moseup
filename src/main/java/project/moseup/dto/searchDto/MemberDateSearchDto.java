package project.moseup.dto.searchDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDateSearchDto {
    String startDate;
    String endDate;
    String keyword = "";
    String orderBy = "";
}
