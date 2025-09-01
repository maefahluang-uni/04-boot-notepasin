package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public static Map<String, User> users = new HashMap<>();

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody(required = false) User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isBlank()) {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
        String username = user.getUsername();
        if (users.containsKey(username)) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        users.put(username, user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> list() {
        return new ResponseEntity<>(users.values(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User found = users.get(username);
        if (found == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}

