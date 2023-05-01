package bo.edu.ucb.todo.api;

import bo.edu.ucb.todo.bl.AuthBl;
import bo.edu.ucb.todo.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
class AuthApi {

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<ResponseDto<TokenDto>> login(@RequestBody LoginDto login) {
        ResponseDto<TokenDto> response = new ResponseDto<>();
        AuthBl authBl = new AuthBl();
        TokenDto tokenDto = authBl.login(login);
        if (tokenDto == null) {
            response.setCode("0001");
            response.setResponse(null);
            response.setErrorMessage("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
            response.setCode("0000");
            response.setResponse(tokenDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
