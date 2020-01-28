package Simulation;

import com.example.SymulatorDziekanatu.WorkerActivities;

import java.util.List;

public class SimulationConfig {
    public int numberOfWorkers;
    public int maxTaskDifficulty;
    public int randomClientsPerProcess;
    public WorkerActivities[] workersSchedule;
    public List<String> customQueue;
}
