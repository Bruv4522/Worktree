package com.example.Worktree.User;

import com.example.Worktree.LoginRes;
import com.example.Worktree.Text;
import com.example.Worktree.Token.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService service;
    private TokenRepo tokenRepo;

    @PostMapping("/signup")
    public Text signup(@RequestBody User user) {
        if (service.signup(user)) {
            return new Text("User created successfully");
        } else {
            return new Text("Failed to create user");
        }
    }

    @PostMapping("/login")
    public LoginRes login(@RequestBody User user) {
        if (service.login(user)) {
            return new LoginRes("Login successful", tokenRepo.findByUserUsername(user.getUsername()).orElseThrow().getId());
        } else {
            return new LoginRes("Invalid credentials", null);
        }
    }

    @PostMapping("/logout")
    public Text logout(@RequestBody User user) {
        if (service.logout(user)) {
            return new Text("Successfully logged out");
        } else {
            return new Text("Failed to log out");
        }
    }
}
