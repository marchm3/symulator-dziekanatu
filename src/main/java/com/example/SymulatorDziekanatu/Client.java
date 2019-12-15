package com.example.SymulatorDziekanatu;

public class Client implements IClient{
    private TaskStrategy tasksStrategy;
    private String type;
    private Integer id;
    private int waitingTime = 0;

    Client(int id, String type, TaskStrategy tasksStrategy) {
        this.tasksStrategy = tasksStrategy;
        this.type = type;
        this.id = id;
    }

    public void provideWorker(Worker worker) {
        tasksStrategy.provideWorker(worker);
    }

    public boolean hasTasks() {
        return tasksStrategy.hasTasks();
    }

    public String getType() {
        return type;
    }

    public int getId() {return id; }

    public void waitInQueue() {
        waitingTime += 1;
    }

    public int getWaitingTime() {
        return waitingTime;
    }
}
