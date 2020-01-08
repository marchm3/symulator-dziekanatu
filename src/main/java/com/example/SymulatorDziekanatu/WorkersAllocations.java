package com.example.SymulatorDziekanatu;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class WorkersAllocations {
    private HashMap<Worker, IClient> map = new HashMap<>();

    void setClientForWorker(Worker worker, IClient client) {
        map.put(worker, client);
    }

    IClient getClientForWorker(Worker worker) {
        return map.get(worker);
    }

    void removeAllocation(Worker worker) {
        map.remove(worker);
    }

    boolean isAllocated(Worker worker) {
        return map.containsKey(worker);
    }

    Collection<IClient> getClients() {
        return map.values();
    }
}
