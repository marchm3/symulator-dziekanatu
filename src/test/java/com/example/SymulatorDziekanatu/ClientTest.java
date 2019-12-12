package com.example.SymulatorDziekanatu;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ClientTest {

    @Test
    void clientShouldDelegateTasksToWorker() {
        //when
        List<Integer> tasks = new LinkedList<>(Arrays.asList(1, 2, 3));
        Client client = new Client(tasks);
        Worker worker = new Worker(99);
        //given
        client.delegateTask(worker);
        client.delegateTask(worker);
        client.delegateTask(worker);
        //then
        assertFalse(client.hasTasks());
    }

    @Test
    void clientShouldBeComparedByPriority() {
        //when
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
        //given
        clients.sort(Client::compareTo);
        //then
        assertEquals(OrderedClients, clients);
    }
}
