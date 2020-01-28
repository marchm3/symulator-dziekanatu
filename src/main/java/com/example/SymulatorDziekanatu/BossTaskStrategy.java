package com.example.SymulatorDziekanatu;

public class BossTaskStrategy implements TaskStrategy {
    Office office;
    int numberOfProcess = 0;
    final int requiredNumberOfProcess = 4;

    BossTaskStrategy(Office office) {
        this.office = office;
    }

    public void provideWorker(Worker worker) {
        if (worker.getCurrentActivity() == WorkerActivities.working) {
            worker.exhaust();
            numberOfProcess += 1;
        } else {
            office.fireWorker(worker);
        }
    }

    public boolean hasTasks() {
        return numberOfProcess != requiredNumberOfProcess;
    }

    public int theoreticalProcessTime(int energyPerTurn) {
        return requiredNumberOfProcess;
    }
}
