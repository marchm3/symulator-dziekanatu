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
public class Simulation {
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
    }

    public Report getReport() {
        return office.getReport();
    }

    public void process(int number) {
        IntStream.range(0, number).forEach(i -> {
            singleProcess();
        });
    }

    private void singleProcess() {
        addRandomClients();
        office.process();
    }


    private void addRandomClients() {
        List<Integer> probability = Arrays.asList(65, 7, 15, 6, 5, 2);
        List<String> clientTypes = Arrays.asList(student, PhD, friend, lecturer, professor, dean);
        IntStream.range(0, config.randomClientsPerProcess).forEach(i -> {
            int draw = rand.nextInt(100);
            for(int j = 0; j <= clientTypes.size(); j++) {
                if(draw < probability.get(j)) {
                    office.addClient(clientTypes.get(j), getRandomTasks());
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
