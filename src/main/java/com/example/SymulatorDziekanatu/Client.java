package com.example.SymulatorDziekanatu;

import java.util.List;

public class Client implements Comparable<Client> {
    List<Integer> tasks;
    private int priority;

    Client(List<Integer> tasks) {
        this.tasks = tasks;
        this.priority = 0;
    }

    Client(List<Integer> tasks, int priority) {
        this.tasks = tasks;
        this.priority = priority;
    }

    void delegateTask(Worker worker) {
        worker.doTask(tasks);
    }

    boolean hasTasks() {
        return !tasks.isEmpty();
    }

    @Override
    public int compareTo(Client client) {
        return this.priority - client.priority;
    }
}
