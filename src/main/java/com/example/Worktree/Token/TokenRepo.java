package com.example.Worktree.Token;

import com.example.Worktree.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepo extends JpaRepository<Token, UUID> {
    void deleteAllByExpiresBefore(Instant now);
    boolean existsByUser(User user);
    Optional<Token> findByUser(User user);
    Optional<Token> findByUserUsername(String username);
}
