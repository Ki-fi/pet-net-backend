package petnet.com.services;

import org.springframework.stereotype.Service;
import petnet.com.dtos.PostServiceInputDto;
import petnet.com.models.Post;
import petnet.com.models.PostService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostServiceManager {

    public List<PostService> convertToEntities(List<PostServiceInputDto> dtoList, Post post) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }

        List<PostService> services = new ArrayList<>();
        for (PostServiceInputDto dto : dtoList) {
            PostService service = new PostService();
            service.setTitle(dto.title);
            service.setDescription(dto.description);
            service.setPost(post);
            services.add(service);
        }
        return services;
    }


}
