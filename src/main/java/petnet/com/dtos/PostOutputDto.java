package petnet.com.dtos;

import petnet.com.models.PostStatus;
import petnet.com.models.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostOutputDto {

    public Long postId;
    public Long creator;
    public LocalDateTime createdAt;
    public String firstName;
    public String preposition;
    public String lastName;
    public LocalDate startDate;
    public LocalDate endDate;
    public String title;
    public String remark;
    public PostStatus postStatus;
    public String avatar;
    public List<ResponseOutputDto> responses;

}
