# Plan laboratorium
Na pierwszym laboratorium zostaną rozlosowane tematy tak, aby zostały utworzone grupy trzyosobowe. Projekt wykonujecie przez wszystkie zjazdy i prezentujecie działanie na ostatnim zjeździe. Należy również przygotować i przesłać kody wraz z dokumentacją.


# Technologia wykonania i architektura
Głównym językiem programowania jest Java. Obowiązuje wersja JDK23 i środowisko programowania IntelliJ. W ramach projektu należy zbudować aplikację sieciową składającą się z komponentów:

- klient tzw gruby w technologii JavaFX charakteryzujący się pełną interaktywnością łączący się z serwerem poprzez własny protokół

- klient tzw cienki w technologii webowej łączący się z serwerem poprzez HTTP. Strona WWW powinna być RWD. W jendym z projektów - pominięty.

- serwer z dostępem do bazy relacyjnej poprzez mapowanie obiektowo - relacyjne (JPA) obsługujący łączność z dwoma typami klienta. Można użyć m. in. SpringBoot

- relacyjna baza danych  (MySQL, PostgreSQL, SQLite)


# Instalacja
Poszczególne komponenty powinny być zainstalowane na różnych hostach w LAN w tym smartfonie.


# Etapy (osobno oceniane)

## Etap 1 ( 1 pkt)
Przygotować opis wstępny zawierający:

- Wymagania funkcjonalne i niefunkcjonalne
- tabelę przypadków użycia
- dowolny diagram UML (np przypadków użycia, sekwencji, aktywności)
- diagram bazy danych (diagram encji lub diagram relacji lub schemat bazy danych)


## Etap 2 (5 pkt)
Wykonanie projektu zgodnie ze sztuką inżynierii oprogramowania z zastosowaniem repozytorium GitHub. Zastosować JavaDOC, testy JUnit.


## Etap 3 (2 pkt)
Prezentacja działania aplikacji na hostach w sieci. Można między innymi zaprezentować przypadki awarii sieci lub któregoś z komponentów, a także pracę kilku klientów na raz.


## Etap 4 (2 pkt)
Dodatkowa dokumentacja uzupełniająca opis wstępny, zawierająca:

- diagramy klas
- opis niestandardowych rozwiązań programistycznych
- wyniki testów (wymyślić i przeprowadzić testowanie)