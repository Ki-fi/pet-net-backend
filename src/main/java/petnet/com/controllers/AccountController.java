package petnet.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petnet.com.dtos.AccountInputDto;
import petnet.com.services.AccountService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/signup")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody AccountInputDto dto) {
        accountService.createAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account aangemaakt!");
    }

}
