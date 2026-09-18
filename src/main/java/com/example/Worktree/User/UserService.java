package com.example.Worktree.User;

import com.example.Worktree.Token.Token;
import com.example.Worktree.Token.TokenRepo;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo repo;
    private final TokenRepo token;

    public boolean signup(User user) {
        try {
            repo.save(new User(user.getUsername(), BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean login(User user) {
        if (!repo.existsByUsername(user.getUsername())) {
            return false;
        }

        User foundUser = repo.findByUsername(user.getUsername());

        if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
            if (!token.existsByUser(foundUser)) {
                token.save(new Token(foundUser));
            }

            return true;
        }

        return false;
    }

    public boolean logout(User user) {
        Optional<Token> foundToken = token.findByUserUsername(user.getUsername());

        if (foundToken.isPresent()) {
            User foundUser = repo.findByUsername(user.getUsername());

            if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
                user.setToken(null);
                token.delete(foundToken.get());
                return true;
            }
        }

        return false;
    }
}
