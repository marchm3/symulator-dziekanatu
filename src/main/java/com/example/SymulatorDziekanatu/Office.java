package com.example.SymulatorDziekanatu;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Office {
    private List<Worker> workers;
    private HashMap<Worker, IClient> handlingMap = new HashMap<>();
    private List<String> handledClients = new ArrayList<>();
    private List<Worker> workersToFire = new LinkedList<>();
    private ClientsQueue clientsQueue = new ClientsQueue();
    private ClientsFactory clientsFactory = new ClientsFactory(this);

    private Office(List<Worker> workers) {
        this.workers = workers;
    }

    void addClient(String type, Integer... tasksDifficulty) {
        clientsQueue.add(clientsFactory.createClient(type, tasksDifficulty));
    }

    void process() {
        resetWorkersEnergy();
        while(!isProcessStuck()) {
            allocateFreeWorkers();
            processAllocatedWorkers();
            executeFiring();
        }
        waitClientInQueue();
    }

    private void resetWorkersEnergy() {
        workers.forEach(Worker::resetEnergy);
    }

    private void waitClientInQueue() {
        Stream.of(clientsQueue.getAll(), handlingMap.values())
                .flatMap(list -> list.stream()).forEach(client -> client.waitInQueue());

    }

    private boolean isProcessStuck() {
        return workers.size() == 0 || workers.stream().map(
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
            IClient client = handlingMap.get(worker);
            client.provideWorker(worker);
            if(!client.hasTasks()) {
                handlingMap.remove(worker);
                handledClients.add(client.getType());
            }
        });
    }

    private void executeFiring() {
        workersToFire.forEach(worker -> {
            if(handlingMap.containsKey(worker)) {
                clientsQueue.add(handlingMap.get(worker));
                handlingMap.remove(worker);
            }
            workers.remove(worker);
        });
        workersToFire.clear();
    }

    void fireWorker(Worker worker) {
        workersToFire.add(worker);
    }

    List<String> getHandledClients() {
        return new ArrayList<>(handledClients);
    }

    List<String> getClientsInQueue() {
        return clientsQueue.getAll().stream().map(client -> client.getType()).collect(Collectors.toList());
    }

    int getNumberOfWorkers() {
        return workers.size();
    }

    Report getReport() {
        Report report = new Report();
        report.differentialsDegrees = clientsFactory.getCreatedClients().stream()
                .filter(c -> c.getType() == "Professor")
                .map(c -> c.getWaitingTime())
                .collect(Collectors.toList());
        report.numberOfExtraTasks = clientsFactory.getCreatedClients().stream()
                .filter(c -> c.getType() == "Lecturer")
                .mapToInt(c -> c.getWaitingTime())
                .sum();
        report.numberOfComplaints = clientsFactory.getCreatedClients().stream()
                .filter(c -> c.getType() == "Friend")
                .mapToInt(c -> c.getWaitingTime())
                .sum();
        report.gradeReductions = clientsFactory.getCreatedClients().stream()
                .filter(c -> c.getType() == "PhD")
                .map(c -> c.getWaitingTime()/2.0)
                .collect(Collectors.toList());
        report.numberOfBeers = clientsFactory.getCreatedClients().stream()
                .filter(c -> c.getType() == "Student")
                .map(c -> c.getWaitingTime())
                .collect(Collectors.toList());
        return report;
    }

    static class Builder {
        private int numberOfWorkers = 1;
        private int workersEnergy = 5;
        private List<WorkerActivities> schedule = Arrays.asList(WorkerActivities.working);

        Builder withNumberOfWorkers(int number) {
            numberOfWorkers = number;
            return this;
        }

        Builder withWorkersEnergy(int energy) {
            workersEnergy = energy;
            return this;
        }

        Builder withWorkersSchedule(WorkerActivities... schedule) {
            this.schedule = Arrays.asList(schedule);
            return this;
        }

        public Office build() {
            List<Worker> workers = IntStream.range(0, numberOfWorkers).boxed()
                    .map(i -> new Worker(workersEnergy, schedule)).collect(Collectors.toList());
            return new Office(workers);
        }
    }
}
