package com.example.SymulatorDziekanatu;

import java.util.*;

public class Report {
    public List<Integer> numberOfBeers = new ArrayList<>();

    public int numberOfComplaints = 0;

    public List<Double> gradeReductions = new ArrayList<>();

    public int numberOfExtraTasks = 0;

    public List<Integer> differentialsDegrees = new ArrayList<>();

    public List<WorkerActivities> currentWorkersActivities = new ArrayList<>();

    public int numberOfClientsInQueue = 0;

    public Map<String, Long> clientsTypeInQueueToNumberMap = new HashMap<>();

    public int theoreticalWaitingTime = 0;

}
