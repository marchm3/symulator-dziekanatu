package com.example.SymulatorDziekanatu;

import java.util.Collections;
import java.util.List;

public class Worker {

    private int energy;
    private int maxEnergy;
    private List<WorkerActivities> schedule;
    private int currentActivityIndex = 0;
    private boolean stuck = false;

    Worker(int energy, List<WorkerActivities> schedule) {
        this.energy = energy;
        this.maxEnergy = energy;
        this.schedule = schedule;
    }

    void doTask(List<Integer> tasks) {
        tasks.stream().findFirst().ifPresent(task -> {
            if(haveEnoughEnergyToDoTask(task)) {
                if (getCurrentActivity() == WorkerActivities.working) {
                    energy -= task;
                    tasks.remove(task);
                }
                goToNextActivity();
            } else {
                stuck = true;
            }
        });
    }

    WorkerActivities getCurrentActivity() {
        return schedule.get(currentActivityIndex);
    }

    void exhaust() {
        stuck = true;
        goToNextActivity();
    }

    boolean isStuck() {
        return stuck;
    }

    void resetEnergy() {
        energy = maxEnergy;
        stuck = false;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    private void goToNextActivity() {
        currentActivityIndex = (currentActivityIndex + 1) % schedule.size();
    }

    private boolean haveEnoughEnergyToDoTask(int task) {
        return energy >= task;
    }
}
