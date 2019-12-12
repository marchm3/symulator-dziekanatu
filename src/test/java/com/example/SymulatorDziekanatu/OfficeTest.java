package com.example.SymulatorDziekanatu;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OfficeTest {

    @Test
    void shouldHandleSingleClient() {
        //when
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker(5));
        Office office = new Office(workers);

        List<Integer> tasks = new ArrayList<>(Arrays.asList(2, 3 ,5));
        Client client = new Client(tasks);
        office.addClient(client);

        //given
        office.process();

        //then
        assertEquals(Arrays.asList(5), tasks);
    }

    @Test
    void shouldHandleMultipleClients() {
        //when
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker(5));
        Office office = new Office(workers);

        List<Integer> tasks = new ArrayList<>(Arrays.asList(1, 2));
        Client client = new Client(tasks, 1);
        office.addClient(client);

        List<Integer> tasks2 = new ArrayList<>(Arrays.asList(2, 3));
        Client client2 = new Client(tasks2, 2);
        office.addClient(client2);

        //given
        office.process();

        //then
        assertTrue(tasks.isEmpty());
        assertEquals(Arrays.asList(3), tasks2);
    }

    @Test
    void shouldHandleMultipleClientsByMultipleWorkers() {
        //when
        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker(5));
        workers.add(new Worker(5));
        Office office = new Office(workers);

        List<Integer> tasks = new ArrayList<>(Arrays.asList(1, 2));
        Client client = new Client(tasks, 1);
        office.addClient(client);

        List<Integer> tasks2 = new ArrayList<>(Arrays.asList(1, 1));
        Client client2 = new Client(tasks2, 2);
        office.addClient(client2);

        List<Integer> tasks3 = new ArrayList<>(Arrays.asList(2, 3));
        Client client3 = new Client(tasks3, 3);
        office.addClient(client3);

        //given
        office.process();

        //then
        assertEquals(Arrays.asList(), tasks);
        assertEquals(Arrays.asList(), tasks2);
        assertEquals(Arrays.asList(3), tasks3);
    }

    Client createClient(int... tasksArray) {
        List<Integer> tasks  = Arrays.stream(tasksArray).boxed().collect(Collectors.toList());
        return new Client(tasks);
    }
}
