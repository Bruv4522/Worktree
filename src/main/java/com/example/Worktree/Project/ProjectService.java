package com.example.Worktree.Project;

import com.example.Worktree.User.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepo repo;

    public List<Project> findAll() {
        return repo.findAll();
    }

    public Optional<Project> findById(long id) {
        return repo.findById(id);
    }

    public List<Project> findByUserId(long userId) {
        return repo.findByUserId(userId);
    }

    public boolean create(Project project, User user) {
        try {
            project.setUser(user);
            repo.save(project);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(long id, Project project) {
        if (!repo.existsById(id)) {
            return false;
        }

        try {
            Project existing = repo.findById(id).orElseThrow();
            existing.setName(project.getName());
            existing.setDescription(project.getDescription());
            existing.setRepo(project.getRepo());
            existing.setUser(project.getUser());
            repo.save(existing);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

