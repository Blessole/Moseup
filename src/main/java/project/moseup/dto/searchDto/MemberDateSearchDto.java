package project.moseup.dto.searchDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MemberDateSearchDto {
    LocalDateTime startDate;
    LocalDateTime endDate;
}
