package com.example.SymulatorDziekanatu;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.SymulatorDziekanatu.ClientTypes.*;

public class ReportGenerator {

    public static Report getReport(List<IClient> allClients, List<IClient> clientsInQueue, List<Worker> workers) {
        Report report = new Report();
        report.differentialsDegrees = allClients.stream()
                .filter(c -> c.getType().equals(professor))
                .map(IClient::getWaitingTime)
                .collect(Collectors.toList());
        report.numberOfExtraTasks = allClients.stream()
                .filter(c -> c.getType().equals(lecturer))
                .mapToInt(IClient::getWaitingTime)
                .sum();
        report.numberOfComplaints = allClients.stream()
                .filter(c -> c.getType().equals(friend))
                .mapToInt(IClient::getWaitingTime)
                .sum();
        report.gradeReductions = allClients.stream()
                .filter(c -> c.getType().equals(PhD))
                .map(c -> c.getWaitingTime() / 2.0)
                .collect(Collectors.toList());
        report.numberOfBeers = allClients.stream()
                .filter(c -> c.getType().equals(student))
                .map(c -> c.getWaitingTime() / 2)
                .collect(Collectors.toList());
        report.currentWorkersActivities = workers.stream()
                .map(Worker::getCurrentActivity)
                .collect(Collectors.toList());
        report.numberOfClientsInQueue = clientsInQueue.size();
        report.clientsTypeInQueueToNumberMap = clientsInQueue.stream().map(IClient::getType)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        report.theoreticalWaitingTime = workers.stream()
                .findFirst()
                .map(Worker::getMaxEnergy)
                .map(maxEnergy -> clientsInQueue.stream()
                        .mapToInt(client -> client.getTheoreticalProcessTime(maxEnergy))
                        .sum() / ((double) workers.size())
                ).map(numberOfProcess -> (int) Math.ceil(numberOfProcess))
                .orElse(-1);
        return report;
    }
}
