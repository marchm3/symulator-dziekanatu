package com.example.SymulatorDziekanatu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class ClientsQueue {
    private PriorityQueue<IClient> queue = new PriorityQueue<>(ClientsQueue::compareClient);

    static public int compareClient(IClient client1, IClient client2) {
        List<String> priorityList = Arrays.asList("Dean", "Professor", "Lecturer", "Friend", "PhD", "Student");
        int priority1 = priorityList.indexOf(client1.getType());
        int priority2 = priorityList.indexOf(client2.getType());
        if(priority1 != priority2)
            return priority1 - priority2;
        return client1.getId() - client2.getId();
    }

    IClient poll() {
        return queue.poll();
    }

    void add(IClient client) {
        queue.add(client);
    }

    List<IClient> getAll() {
        return new ArrayList<>(queue);
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}
