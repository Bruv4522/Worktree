package com.example.Worktree.User;

import com.example.Worktree.Token.Token;
import com.example.Worktree.Token.TokenRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo repo;
    private final TokenRepo token;

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> findById(long id) {
        return repo.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Optional<User> findByToken(String token) {
        return repo.findByTokenId(UUID.fromString(token));
    }

    @Transactional
    public boolean signup(User user) {
        try {
            repo.save(new User(user.getUsername(), BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean login(User user) {
        if (!repo.existsByUsername(user.getUsername())) {
            return false;
        }

        User foundUser = repo.findByUsername(user.getUsername()).orElseThrow();

        if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
            if (!token.existsByUser(foundUser)) {
                token.save(new Token(foundUser));
            }

            return true;
        }

        return false;
    }

    @Transactional
    public boolean logout(User user) {
        Optional<Token> foundToken = token.findByUserUsername(user.getUsername());

        if (foundToken.isPresent()) {
            User foundUser = repo.findByUsername(user.getUsername()).orElseThrow();

            if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
                foundUser.setToken(null);
                token.delete(foundToken.get());
                return true;
            }
        }

        return false;
    }
}
