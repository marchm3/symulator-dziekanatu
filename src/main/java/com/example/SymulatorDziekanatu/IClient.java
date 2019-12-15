package com.example.SymulatorDziekanatu;

public interface IClient {
    void provideWorker(Worker worker);

    boolean hasTasks();

    String getType();

    int getId();

    void waitInQueue();

    int getWaitingTime();
}
