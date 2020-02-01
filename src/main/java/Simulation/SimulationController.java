package Simulation;

import com.example.SymulatorDziekanatu.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController()
public class SimulationController {
    @Autowired
    SimulationService simulation;


    @PostMapping("/start")
    public void startNew(@Valid @NotNull @RequestBody SimulationConfig config) {
        simulation.startNew(config);
    }

    @PostMapping("/process")
    public ResponseEntity process(@RequestParam(defaultValue = "1") @Min(1) Integer number) {
        try {
            simulation.process(number);
        } catch (SimulationEndedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Simulation has ended");
        } catch (SimulationNotStartedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Simulation not started");
        }
        return ResponseEntity.ok(simulation.getReport());
    }

    @GetMapping("/report")
    public ResponseEntity getReport() {
        try {
            return ResponseEntity.ok(simulation.getReport());
        } catch (SimulationNotStartedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Simulation not started");
        }
    }

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }
}
