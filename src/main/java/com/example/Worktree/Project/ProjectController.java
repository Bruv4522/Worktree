package com.example.Worktree.Project;

import com.example.Worktree.Text;
import com.example.Worktree.User.User;
import com.example.Worktree.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {
    private ProjectService service;
    private UserService users;

    @GetMapping
    public List<Project> findAll(@RequestHeader("Authorization") String token) {
        Optional<User> foundUser;

        try {
            foundUser = users.findByToken(token);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (!foundUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return service.findByUserId(foundUser.get().getId());
    }
}
