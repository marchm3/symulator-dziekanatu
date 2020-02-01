package com.example.SymulatorDziekanatu;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ClientsFactory {
    private int numberOfCreatedClients = 0;

    private List<IClient> createdClients = new ArrayList<>();

    Office office;

    ClientsFactory(Office office) {
        this.office = office;
    }

    public IClient createClient(String type, Integer... tasksDifficulty) {
        List<Integer> tasks = Lists.newArrayList(tasksDifficulty);
        numberOfCreatedClients += 1;
        TaskStrategy taskStrategy = new ClientTaskStrategy(tasks);
        if (type.equals("Dean")) {
            taskStrategy = new BossTaskStrategy(office);
        }
        IClient client = new Client(numberOfCreatedClients, type, taskStrategy);
        createdClients.add(client);
        return client;
    }

    public List<IClient> getCreatedClients() {
        return new ArrayList<>(createdClients);
    }
}
