package Simulation;

import com.example.SymulatorDziekanatu.Office;
import com.example.SymulatorDziekanatu.Report;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import static com.example.SymulatorDziekanatu.ClientTypes.*;

@Service
public class SimulationService {
    Office office;
    SimulationConfig config;
    Random rand = new Random();

    public void startNew(SimulationConfig config) {
        this.config = config;
        office = new Office.Builder()
                .withNumberOfWorkers(config.numberOfWorkers)
                .withWorkersSchedule(config.workersSchedule)
                .withWorkersEnergy(config.maxTaskDifficulty)
                .build();
        if(config.customQueue != null) {
            config.customQueue.forEach(clientType -> {
                office.addClient(clientType, getRandomTasks());
            });
        }
    }

    public Report getReport() {
        if(office == null) {
            throw new SimulationNotStartedException();
        }
        return office.getReport();
    }

    public void process(int number) {
        if(office == null) {
            throw new SimulationNotStartedException();
        } if(simulationEnded()) {
            throw  new SimulationEndedException();
        }
        IntStream.range(0, number).forEach(i -> {
            singleProcess();
        });
    }

    private void singleProcess() {
        if(config.customQueue == null && !simulationEnded()) {
            addRandomClients();
        }
        office.process();
    }

    private boolean simulationEnded() {
        return !office.hasWorkers() || (config.customQueue != null && office.getClientsInQueue().size() == 0);
    }


    private void addRandomClients() {
        List<Integer> probability = Arrays.asList(65, 7, 15, 6, 5, 2);
        List<String> clientTypes = Arrays.asList(student, PhD, friend, lecturer, professor, dean);
        IntStream.range(0, config.randomClientsPerProcess).forEach(i -> {
            int draw = rand.nextInt(100);
            for(int j = 0; j < clientTypes.size(); j++) {
                if(draw < probability.get(j)) {
                    office.addClient(clientTypes.get(j), getRandomTasks());
                    break;
                } else {
                    draw -= probability.get(j);
                }
            }
        });
    }

    private Integer[] getRandomTasks() {
        List<Integer> tasks = new ArrayList<>();
        tasks.add(rand.nextInt(config.maxTaskDifficulty) + 1);
        tasks.add(rand.nextInt(config.maxTaskDifficulty) + 1);
        tasks.add(rand.nextInt(config.maxTaskDifficulty) + 1);
        return tasks.toArray(new Integer[tasks.size()]);
    }
}
