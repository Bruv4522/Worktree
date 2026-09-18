package com.example.Worktree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WorktreeApplication {
	static void main(String[] args) {
		SpringApplication.run(WorktreeApplication.class, args);
	}

    @GetMapping
    public Text home() {
        return new Text("Hello world!");
    }
}
