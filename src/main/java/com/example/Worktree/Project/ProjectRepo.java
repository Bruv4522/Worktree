package com.example.Worktree.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findByUserId(long id);
    List<Project> findByAiredTrue();
}

