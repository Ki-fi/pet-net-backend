package petnet.com.dtos;

import java.time.LocalDate;
import java.util.List;

public class PostInputDto {

    public LocalDate startDate;
    public LocalDate endDate;
    public String title;
    public String remark;
    public Long userId;
    public List<PostServiceInputDto> services;

}
