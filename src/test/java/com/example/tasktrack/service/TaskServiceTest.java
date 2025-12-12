package com.example.tasktrack.service;

import com.example.tasktrack.model.Task;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskServiceTest {

    private static final String TEST_FILE = "test-tasks.json";
    private TaskService service;

    @BeforeAll
    void beforeAll() throws Exception {
        // ensure previous test file is removed
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @BeforeEach
    void setUp() {
        service = new TaskService(TEST_FILE);
    }

    @Test
    void addAndListTest() {
        Task t = service.add("TestTitle", "desc", "2025-12-31");
        List<Task> list = service.list();
        // Assertions.assertTrue(list.stream().anyMatch(x -> x.getId().equals(t.getId())));
        Assertions.assertEquals(5, list.size());

    }
}
