package com.example.SymulatorDziekanatu;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static java.util.Arrays.*;
import java.util.stream.IntStream;
import static com.example.SymulatorDziekanatu.WorkerActivities.*;
import static com.example.SymulatorDziekanatu.ClientTypes.*;

class OfficeTest {

    @Test
    void shouldHandleMultipleClients() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(1).withWorkersEnergy(5).build();

        //given
        office.addClient(PhD, 1,2);
        office.addClient(friend, 2);
        office.addClient(student, 1);
        office.process();

        //then
        assertEquals(asList(friend, PhD), office.getHandledClients());
    }

    @Test
    void shouldHandleMultipleClientsByMultipleWorkers() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(2).withWorkersEnergy(5).build();

        //given
        office.addClient(friend, 1,2);
        office.addClient(PhD, 1,2);
        office.addClient(student, 2);
        office.addClient(student, 99);
        office.addClient(student, 99);
        office.addClient(student, 99);
        office.process();

        //then
        assertEquals(asList(friend, PhD, student), office.getHandledClients());
        assertEquals(asList(student), office.getClientsInQueue());
    }

    @Test
    void deanShouldFireWorkerWhenCatchOnBreak() {
        //when
        Office office = new Office.Builder()
                .withNumberOfWorkers(1)
                .withWorkersEnergy(5)
                .withWorkersSchedule(working, phone)
                .build();

        //given
        office.addClient(dean);
        office.process();
        office.process();

        //then
        assertEquals(0, office.getNumberOfWorkers());
        assertEquals(asList(dean), office.getClientsInQueue());
    }

    @Test
    void deanShouldTakeWorkerFor4Process() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(1).withWorkersEnergy(5).build();

        //given
        office.addClient(dean);
        office.addClient(student, 1);
        office.process(4);

        //then
        assertEquals(asList(dean), office.getHandledClients());
        assertEquals(asList(student), office.getClientsInQueue());
    }

    @Test
    void shouldGenerateCorrectRelieveReport() {
        //when
        Office office = new Office.Builder().withNumberOfWorkers(1).withWorkersEnergy(5).build();

        //given
        office.addClient(student, 5);
        office.addClient(student, 5);
        office.addClient(student, 5);
        office.addClient(PhD, 5);
        office.addClient(PhD, 5);
        office.addClient(friend, 5);
        office.addClient(friend, 5);
        office.addClient(lecturer, 5);
        office.addClient(lecturer, 5);
        office.addClient(professor, 5);
        office.addClient(professor, 5);
        office.process(10);

        //then
        Report report = office.getReport();
        assertEquals(asList(0,1), report.differentialsDegrees);
        assertEquals(5, report.numberOfExtraTasks);
        assertEquals(9, report.numberOfComplaints);
        assertEquals(asList(3.0, 3.5), report.gradeReductions);
        assertEquals(asList(4,4,5), report.numberOfBeers);
    }

    @Test
    void shouldGenerateCorrectWorkersActivityReport() {
        //when
        Office office = new Office.Builder()
                .withNumberOfWorkers(3)
                .withWorkersEnergy(1)
                .withWorkersSchedule(
                        working, soup,
                        working, smoking,
                        working, phone
                ).build();

        //given
        office.addClient(student, 1);
        office.addClient(student, 1, 1);
        office.addClient(student, 1, 1, 1);
        office.process(5);

        //then
        Report report = office.getReport();
        assertEquals(asList(soup, smoking, phone), report.currentWorkersActivities);
    }

    @Test
    void shouldGenerateCorrectClientsInQueueReport() {

    }

    @Test
    void shouldGenerateCorrectTheoreticalWaitingTimeReport() {
        //when
        Office office = new Office.Builder()
                .withNumberOfWorkers(2)
                .withWorkersEnergy(3)
                .build();
        //given
        office.addClient(student, 1, 2, 3);
        office.addClient(student, 3, 3);
        office.addClient(dean);
        //then
        Report report = office.getReport();
        assertEquals(4, report.theoreticalWaitingTime);
    }
}
