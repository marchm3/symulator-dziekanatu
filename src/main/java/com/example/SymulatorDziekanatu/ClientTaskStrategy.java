package com.example.SymulatorDziekanatu;

import java.util.List;

public class ClientTaskStrategy implements TaskStrategy {
    private List<Integer> tasks;

    ClientTaskStrategy(List<Integer> tasks) {
        this.tasks = tasks;
    }

    public void provideWorker(Worker worker) {
        worker.doTask(tasks);
    }

    public boolean hasTasks() {
        return !tasks.isEmpty();
    }

    public int theoreticalProcessTime(int energyPerTurn) {
        return tasks.stream().mapToInt(Integer::intValue).sum() / energyPerTurn;
    }
}
