package kr.co.fastcam.eatgo.interfaces;

import kr.co.fastcam.eatgo.application.UserService;
import kr.co.fastcam.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        List<User> users = userService.getUsers();

        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(
            @RequestBody User resource) throws URISyntaxException {

        String email = resource.getEmail();
        String name = resource.getName();

        User user = userService.addUser(email, name);

        String url = "/users/"+ user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestBody User resource){
        String email = resource.getEmail();
        String name = resource.getName();
        Long level = resource.getLevel();

        userService.updateUser(id,email, name, level);
        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.deactiveUser(id);
        return "{}";
    }
}
