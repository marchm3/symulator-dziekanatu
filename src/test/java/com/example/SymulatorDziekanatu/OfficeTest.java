package com.example.SymulatorDziekanatu;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.stream.IntStream;

class OfficeTest {

    @Test
    void shouldHandleMultipleClients() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(1).withWorkersEnergy(5).build();

        //given
        office.addClient("PhD", 1,2);
        office.addClient("Friend", 2);
        office.addClient("Student", 1);
        office.process();

        //then
        assertEquals(Arrays.asList("Friend", "PhD"), office.getHandledClients());
    }

    @Test
    void shouldHandleMultipleClientsByMultipleWorkers() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(2).withWorkersEnergy(5).build();

        //given
        office.addClient("Friend", 1,2);
        office.addClient("PhD", 1,2);
        office.addClient("Student", 2);
        office.addClient("Student", 99);
        office.addClient("Student", 99);
        office.addClient("Student", 99);
        office.process();

        //then
        assertEquals(Arrays.asList("Friend", "PhD", "Student"), office.getHandledClients());
        assertEquals(Arrays.asList("Student"), office.getClientsInQueue());
    }

    @Test
    void deanShouldFireWorkerWhenCatchOnBreak() {
        //when
        Office office = new Office.Builder()
                .withNumberOfWorkers(1)
                .withWorkersEnergy(5)
                .withWorkersSchedule(WorkerActivities.working, WorkerActivities.phone)
                .build();

        //given
        office.addClient("Dean");
        office.process();
        office.process();

        //then
        assertEquals(0, office.getNumberOfWorkers());
        assertEquals(Arrays.asList("Dean"), office.getClientsInQueue());
    }

    @Test
    void deanShouldTakeWorkerFor4Process() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(1).withWorkersEnergy(5).build();

        //given
        office.addClient("Dean");
        office.addClient("Student", 1);
        office.process();
        office.process();
        office.process();
        office.process();

        //then
        assertEquals(Arrays.asList("Dean"), office.getHandledClients());
        assertEquals(Arrays.asList("Student"), office.getClientsInQueue());
    }

    @Test
    void shouldGenerateCorrectReport() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(1).withWorkersEnergy(5).build();

        //given
        IntStream.range(0, 2).forEach(i -> {
            office.addClient("Student", 5);
            office.addClient("PhD", 5);
            office.addClient("Friend", 5);
            office.addClient("Lecturer", 5);
            office.addClient("Professor", 5);
        });
        IntStream.range(0, 10).forEach(i -> {
            office.process();
        });

        //then
        Report report = office.getReport();
        assertEquals(Arrays.asList(0,1), report.differentialsDegrees);
        assertEquals(5, report.numberOfExtraTasks);
        assertEquals(9, report.numberOfComplaints);
        assertEquals(Arrays.asList(3.0, 3.5), report.gradeReductions);
        assertEquals(Arrays.asList(8,9), report.numberOfBeers);
    }
}
