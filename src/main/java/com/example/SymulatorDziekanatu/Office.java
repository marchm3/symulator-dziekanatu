package com.example.SymulatorDziekanatu;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static com.example.SymulatorDziekanatu.ClientTypes.*;

public class Office {
    private List<Worker> workers;
    private WorkersAllocations allocations = new WorkersAllocations();
    private List<String> handledClients = new ArrayList<>();
    private List<Worker> workersToFire = new LinkedList<>();
    private ClientsQueue clientsQueue = new ClientsQueue();
    private ClientsFactory clientsFactory = new ClientsFactory(this);

    private Office(List<Worker> workers) {
        this.workers = workers;
    }

    public void addClient(String type, Integer... tasksDifficulty) {
        clientsQueue.add(clientsFactory.createClient(type, tasksDifficulty));
    }

    public void process() {
        resetWorkersEnergy();
        while(!isProcessStuck()) {
            allocateFreeWorkers();
            processAllocatedWorkers();
            executeFiring();
        }
        waitClientInQueue();
    }

    public void process(int number) {
        IntStream.range(0, number).forEach(i -> {
            process();
        });
    }

    public boolean hasWorkers() {
        return workers.size() > 0;
    }

    private void resetWorkersEnergy() {
        workers.forEach(Worker::resetEnergy);
    }

    private void waitClientInQueue() {
        Stream.of(clientsQueue.getAll(), allocations.getClients())
                .flatMap(list -> list.stream()).forEach(client -> client.waitInQueue());
    }

    private boolean isProcessStuck() {
        return workers.isEmpty() || workers.stream().allMatch(
                w -> w.isStuck() || (!allocations.isAllocated(w) && clientsQueue.isEmpty())
        );
    }

    private void allocateFreeWorkers() {
        workers.stream().filter(
                worker -> !allocations.isAllocated(worker) && !clientsQueue.isEmpty()
        ).forEach(worker -> allocations.setClientForWorker(worker, clientsQueue.poll()));
    }

    private void processAllocatedWorkers() {
        workers.stream().filter(
                worker -> !worker.isStuck() && allocations.isAllocated(worker)
        ).forEach(worker -> {
            IClient client = allocations.getClientForWorker(worker);
            client.provideWorker(worker);
            if(!client.hasTasks()) {
                allocations.removeAllocation(worker);
                handledClients.add(client.getType());
            }
        });
    }

    private void executeFiring() {
        workersToFire.forEach(worker -> {
            if(allocations.isAllocated(worker)) {
                clientsQueue.add(allocations.getClientForWorker(worker));
                allocations.removeAllocation(worker);
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

    public List<String> getClientsInQueue() {
        return clientsQueue.getAll().stream().map(client -> client.getType()).collect(Collectors.toList());
    }

    public int getNumberOfWorkers() {
        return workers.size();
    }

    public Report getReport() {
        return ReportGenerator.getReport(clientsFactory.getCreatedClients(), clientsQueue.getAll(), workers);
    }

    public static class Builder {
        private int numberOfWorkers = 1;
        private int workersEnergy = 5;
        private List<String> schedule = Arrays.asList(WorkerActivities.working);

        public Builder withNumberOfWorkers(int number) {
            numberOfWorkers = number;
            return this;
        }

        public Builder withWorkersEnergy(int energy) {
            workersEnergy = energy;
            return this;
        }

        public Builder withWorkersSchedule(String... schedule) {
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
