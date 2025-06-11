# ZPO - Lista obecności 

# Wrzucić pliki docx(i pdf) z teams po skończeniu ich pisania



# Projekt 2 Lista obecności - opis zadania

## Zaimplementuj listę obecności dla studentów.

Student to obiekt, posiadający standardowe pola: imię, nazwisko, numer indeksu.

Student przydzielony jest do grupy. Grupa składa się ze studentów z utworzonej wcześniej listy.

## Opracuj listę przypadków użycia dla nauczyciela (aplikacja desktop JavaFX):
•	Dodaj / usuń studenta
•	Dodaj / usuń grupy
•	Dodaj / usuń studenta z grupy
•	Dodaj termin dla grupy
•	Sprawdź obecności (obecny, spóźniony, nieobecny)
•	Wyświetl dziennik obecności

## Opracuj listę przypadków użycia dla Studenta (strona RWD do wyświetlenia na telefonie):
•	Zaloguj się podając numer albumu. Widzisz dwoje dane, w tym grupę.
•	Przewijaj ekran wyświetlając poszczególne obecności.

## Opracuj strukturę obiektową, czyli odpowiednie klasy:
•	Student
•	Grupa
•	Termin
•	Obecność

i relacje many-to-one (lub many-to-many) 
(przykład: http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example/). 
Baza danych powinna zawierać tabelę grup i tabelę studentów.
poradnik jak to odpalic:
latest mysql w dockerze, bind portow jak tutaj
![image](https://github.com/user-attachments/assets/8988e733-0537-414a-a8d6-8509945ea759)
zmienne srodowiskowe jak tutaj
![image](https://github.com/user-attachments/assets/6e13e9c8-c1a1-4961-ae35-2f03e1e01aed)
i odpalasz apke baza danych sie tworzy na dev jest create drop wiec sie zeruje zawsze na normalnych ustawieniach mamy update czyli tylko tworzy tabele ktorych nie bylo


