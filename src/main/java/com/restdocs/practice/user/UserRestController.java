package com.restdocs.practice.user;

import com.restdocs.practice.core.ServiceException;
import com.restdocs.practice.user.dto.SignUpRequest;
import com.restdocs.practice.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository userRepository;

    @GetMapping("/apis/users/{id}")
    public ResponseEntity<UserResponse> id(@PathVariable long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(404, "There is no User By " + id));
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("/apis/users")
    public ResponseEntity<Void> create(@RequestBody SignUpRequest request) {
        User user = userRepository.save(request.toUser());
        return ResponseEntity.created(URI.create("/apis/users/" + user.getId())).build();
    }

    @DeleteMapping("/apis/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
