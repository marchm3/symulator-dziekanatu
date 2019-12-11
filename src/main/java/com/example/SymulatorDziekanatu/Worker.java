package com.example.SymulatorDziekanatu;

import java.util.Collections;
import java.util.List;

public class Worker {

    private int energy;
    private int maxEnergy;
    private List<WorkerActivities> schedule;
    private int currentActivityIndex = 0;

    Worker(int energy) {
        this.energy = energy;
        this.maxEnergy = energy;
        this.schedule = Collections.singletonList(WorkerActivities.working);
    }

    Worker(int energy, List<WorkerActivities> schedule) {
        this.energy = energy;
        this.maxEnergy = energy;
        this.schedule = schedule;
    }

    void doTask(List<Integer> tasks) {
        if(!tasks.isEmpty() && energy >= tasks.get(0)) {
            if (getCurrentActivity() == WorkerActivities.working) {
                energy -= tasks.get(0);
                tasks.remove(0);
            }
            currentActivityIndex = (currentActivityIndex + 1) % schedule.size();
        }
    }

    WorkerActivities getCurrentActivity() {
        return schedule.get(currentActivityIndex);
    }

    void beginNextRound() {
        energy = maxEnergy;
    }
}
