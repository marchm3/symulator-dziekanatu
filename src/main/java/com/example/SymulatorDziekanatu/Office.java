package com.example.SymulatorDziekanatu;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Office {
    private List<Worker> workers;
    private PriorityQueue<Client> clientsQueue = new PriorityQueue<>();
    private HashMap<Worker, Client> handlingMap = new HashMap<>();

    Office(List<Worker> workers) {
        this.workers = workers;
    }

    void addClient(Client client) {
        clientsQueue.add(client);
    }

    void process() {
        workers.forEach(worker -> {
            worker.resetEnergy();
        });
        while(true) {
            allocateFreeWorkers();
            processAllocatedWorkers();
            if(isStuck()) {
                return;
            }
        }
    }

    private boolean isStuck() {
        return workers.stream().map(
                w -> w.isStuck() || (!handlingMap.containsKey(w) && clientsQueue.isEmpty())
        ).reduce((a, b) -> a && b).get();
    }

    private void allocateFreeWorkers() {
        workers.stream().filter(
                worker -> !handlingMap.containsKey(worker) && !clientsQueue.isEmpty()
        ).forEach(worker -> handlingMap.put(worker, clientsQueue.poll()));
    }

    private void processAllocatedWorkers() {
        workers.stream().filter(
                worker -> !worker.isStuck() && handlingMap.containsKey(worker)
        ).forEach(worker -> {
            Client client = handlingMap.get(worker);
            client.delegateTask(worker);
            if(!client.hasTasks()) {
                handlingMap.remove(worker);
            }
        });
    }

}
