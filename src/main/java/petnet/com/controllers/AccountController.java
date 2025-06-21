package petnet.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petnet.com.dtos.AccountInputDto;
import petnet.com.services.AccountService;

@RestController
@RequestMapping("/signup")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping
    public ResponseEntity<AccountInputDto> signUp(@RequestBody AccountInputDto dto) {
        accountService.createAccount(dto);
        return ResponseEntity.ok().build();
    }

}
