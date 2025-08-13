package petnet.com.mappers;

import org.springframework.stereotype.Component;
import petnet.com.dtos.PostInputDto;
import petnet.com.dtos.PostOutputDto;
import petnet.com.dtos.PostServiceOutputDto;
import petnet.com.dtos.ResponseOutputDto;
import petnet.com.models.*;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostMapper {

    public Post toPostEntity(PostInputDto dto, User creator, List<PostService> services) {

        Post post = new Post();
        post.setTitle(dto.title);
        post.setStartDate(dto.startDate);
        post.setEndDate(dto.endDate);
        post.setRemark(dto.remark);
        post.setPostStatus(PostStatus.ACTIVE);
        post.setCreatedAt(LocalDateTime.now());
        post.setCreator(creator);
        post.setServices(services);

        return post;
    }

    public PostOutputDto toPostOutputDto(Post post) {

        PostOutputDto dto = new PostOutputDto();
        User creator = post.getCreator();
        dto.firstName = creator.getFirstName();
        dto.preposition = creator.getPreposition();
        dto.lastName = creator.getLastName();
        dto.avatar = "/users/" + creator.getUserId() + "/avatar";

        dto.postId = post.getPostId();
        dto.creator = creator.getUserId();
        dto.createdAt = post.getCreatedAt();
        dto.startDate = post.getStartDate();
        dto.endDate = post.getEndDate();
        dto.title = post.getTitle();
        dto.remark = post.getRemark();
        dto.postStatus = post.getPostStatus();

        dto.responses = post.getResponses().stream()
                .map(this::mapResponse)
                .toList();

        dto.services = post.getServices().stream()
                .map(this::mapService)
                .toList();

        return dto;
    }

    private ResponseOutputDto mapResponse(Response response) {
        User user = response.getUserId();
        ResponseOutputDto dto = new ResponseOutputDto();
        dto.responseId = response.getResponseId();
        dto.comment = response.getComment();
        dto.createdAt = response.getCreatedAt();
        dto.userId = user.getUserId();
        dto.firstName = user.getFirstName();
        dto.preposition = user.getPreposition();
        dto.lastName = user.getLastName();
        dto.avatar = "/users/" + user.getUserId() + "/avatar";
        return dto;
    }

    private PostServiceOutputDto mapService(PostService service) {
        PostServiceOutputDto dto = new PostServiceOutputDto();
        dto.serviceId = service.getServiceId();
        dto.title = service.getTitle();
        dto.description = service.getDescription();
        return dto;
    }
}


