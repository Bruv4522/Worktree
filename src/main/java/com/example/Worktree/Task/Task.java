package com.example.Worktree.Task;

import com.example.Worktree.Project.Project;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {
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

    private int priority;
    private boolean complete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private Project project;

    public Task(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;

        if (this.priority > 5) {
            this.priority = 5;
        }

        if (this.priority < 1) {
            this.priority = 1;
        }
    }
}
