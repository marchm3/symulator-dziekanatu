package com.example.SymulatorDziekanatu;

public class BossTaskStrategy implements TaskStrategy {
    Office office;
    int numberOfProvision = 0;
    final int requiredNumberOfProvision = 4;

    BossTaskStrategy(Office office) {
        this.office = office;
    }

    public void provideWorker(Worker worker) {
        if (worker.getCurrentActivity() == WorkerActivities.working) {
            worker.exhaust();
            numberOfProvision += 1;
        } else {
            office.fireWorker(worker);
        }
    }

    public boolean hasTasks() {
        return numberOfProvision != requiredNumberOfProvision;
    }
}
