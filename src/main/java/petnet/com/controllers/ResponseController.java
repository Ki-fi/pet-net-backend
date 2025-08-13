package petnet.com.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import petnet.com.dtos.ResponseInputDto;
import petnet.com.services.ResponseService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/responses")
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    public ResponseEntity<String> addResponse(@RequestBody ResponseInputDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        responseService.createResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Reactie verstuurd");
    }

}
