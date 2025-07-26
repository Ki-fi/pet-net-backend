package petnet.com.dtos;

import petnet.com.models.PostStatus;

import java.time.LocalDate;

public class PostOutputDto {

    public Long postId;
    public Long creator;
    public String firstName;
    public String preposition;
    public String lastName;
    public LocalDate startDate;
    public LocalDate endDate;
    public String title;
    public String remark;
    public PostStatus postStatus;
    public String avatar;

}
