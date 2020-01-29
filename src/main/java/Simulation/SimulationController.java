package Simulation;

import com.example.SymulatorDziekanatu.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimulationController {
    @Autowired
    Simulation simulation;


    @PostMapping("/start")
    public void startNew(@RequestBody SimulationConfig config) {

    }

    @PostMapping("/process")
    public void startNew(@RequestParam Integer number) {

    }

    @GetMapping("/report")
    public Report getReport() {
        return simulation.getReport();
    }
}
