package com.example.SymulatorDziekanatu;

public interface TaskStrategy {
    void provideWorker(Worker worker);

    boolean hasTasks();
}
