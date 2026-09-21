package com.example.Worktree.Project;

import com.example.Worktree.Task.Task;
import com.example.Worktree.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        }
)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private URI repo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    private boolean aired = true;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("priority ASC")
    @JsonManagedReference
    private List<Task> tasks = new ArrayList<>();

    public Project(String name, String description, String repo) {
        this.name = name;
        this.description = description;
        this.repo = URI.create(repo);
    }

    public Project(String name, String description, String repo, boolean aired) {
        this.name = name;
        this.description = description;
        this.repo = URI.create(repo);
        this.aired = aired;
    }

    public Project(String name, String description, String repo, boolean aired, List<Task> tasks) {
        this.name = name;
        this.description = description;
        this.repo = URI.create(repo);
        this.aired = aired;
        this.tasks = tasks;
    }
}
