#Symulator Dziekanatu

Aplikacja działa przez interfejs RESTowy po uruchomieniu SimulationApp
Aby rozpoczać symulacje należy wysłać POSTem konfiguracje na /start
Aby wykonać ture należy wysłać zapytanie POSTem na /process

W pliku example_1.json jest konfiguracja do symulacji z losowymi klientami

W pliku example_2.json jest konfiguracja do symulacji z ustalonymi klientami

/process zwraca raport o aktualnym stanie dziekanatu

numberOfBeers -  lista ilości piw jaką poszczególny student musi wypić
numberOfComplaints - liczba narzekań
gradeReductions - lista obniżeń oceny dla poszczególnych doktorantów
numberOfExtraTasks - liczba dodatkowych zadań
differentialsDegrees - lista rzędów różniczek dla poszczególnych profesorów
currentWorkersActivities - lista aktualnych zajęć dla poszczególnych pracowników
numberOfClientsInQueue - liczba klientów w kolejce 
clientsTypeInQueueToNumberMap - liczba klientów dla poszczególnych typów klientów
theoreticalWaitingTime - teoretyczny czas oczekiwania
