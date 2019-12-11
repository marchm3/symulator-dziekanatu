package com.example.SymulatorDziekanatu;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ClientTest {

    @Test
    void clientShouldDelegateTasksToWorker() {
        List<Integer> tasks = new LinkedList<>(Arrays.asList(1, 2, 3));
        Client client = new Client(tasks);
        Worker worker = new Worker(99);
        client.handle(worker);
        client.handle(worker);
        client.handle(worker);
        assertFalse(client.hasTasks());
    }

    @Test
    void clientShouldBeComparedByPriority() {
        List<Integer> tasks = new LinkedList<>();
        Client veryImportantClient = new Client(tasks, 0);
        Client importantClient = new Client(tasks , 1);
        Client notImportantClient = new Client(tasks, 2);
        List<Client> clients = new LinkedList<>(Arrays.asList(
                importantClient,
                veryImportantClient,
                notImportantClient
        ));
        List<Client> OrderedClients = new LinkedList<>(Arrays.asList(
                veryImportantClient,
                importantClient,
                notImportantClient
        ));
        clients.sort(Client::compareTo);
        assertEquals(OrderedClients, clients);
    }
}
