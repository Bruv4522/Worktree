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
    public List<Project> findAll() {
        return service.findByAired();
    }

    @GetMapping("/me")
    public List<Project> findMine(@RequestHeader("Authorization") String token) {
        Optional<User> foundUser = users.verify(token);
        return service.findByUserId(foundUser.get().getId());
    }

    @PostMapping("/create")
    public Text createProject(@RequestHeader("Authorization") String token, @RequestBody Project project) {
        Optional<User> foundUser = users.verify(token);

        if (service.create(project, foundUser.get())) {
            return new Text("Project created successfully");
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
