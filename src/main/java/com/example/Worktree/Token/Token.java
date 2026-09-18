package com.example.Worktree.Token;

import com.example.Worktree.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(mappedBy = "token")
    private User user;
    private Instant created = Instant.now();
    private Instant expires = created.plus(7, ChronoUnit.SECONDS);

    public Token(User user) {
        this.user = user;
        user.setToken(this);
    }
}
