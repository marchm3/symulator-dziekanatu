package Simulation;

import com.example.SymulatorDziekanatu.WorkerActivities;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SimulationConfig {
    @Min(1)
    public int numberOfWorkers;
    @Min(1)
    public int maxTaskDifficulty;
    public int randomClientsPerProcess;
    @NotNull
    public String[] workersSchedule;
    public List<String> customQueue;
}
