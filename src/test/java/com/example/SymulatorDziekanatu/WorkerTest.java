package com.example.SymulatorDziekanatu;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkerTest {

    @Test
    void workerShouldDoTasks() {
        int energy = 10;
        List<Integer> tasks = new ArrayList<>(Arrays.asList(1, 4, 8));
        Worker worker = new Worker(energy);
        worker.doTask(tasks);
        worker.doTask(tasks);
        worker.doTask(tasks);
        assertEquals(Arrays.asList(8), tasks);

        worker.beginNextRound();
        worker.doTask(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void workerShouldNotDoTasksWhenIsNotWorking() {
        int energy = 99;
        List<WorkerActivities> schedule = Arrays.asList(
                WorkerActivities.phone,
                WorkerActivities.working
        );
        Worker worker = new Worker(energy, schedule);
        List<Integer> tasks = new ArrayList<>(Arrays.asList(1, 4, 8));
        worker.doTask(tasks);
        assertEquals(Arrays.asList(1, 4, 8), tasks);
        worker.doTask(tasks);
        assertEquals(Arrays.asList(4, 8), tasks);
        worker.doTask(tasks);
        assertEquals(Arrays.asList(4, 8), tasks);
        worker.doTask(tasks);
        assertEquals(Arrays.asList(8), tasks);
    }
}
