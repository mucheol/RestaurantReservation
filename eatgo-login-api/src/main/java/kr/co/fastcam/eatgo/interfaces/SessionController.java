package kr.co.fastcam.eatgo.interfaces;

import kr.co.fastcam.eatgo.application.UserService;
import kr.co.fastcam.eatgo.domain.User;
import kr.co.fastcam.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource) throws URISyntaxException {

        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);

        String accessToken = jwtUtil.createToken(
                user.getId(),
                user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() : null);
        //토큰 생성

        String url = "/session";
        return ResponseEntity.created(new URI(url))
                .body(SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build());
    }
}
