# Instrukcja budowy i uruchamiania aplikacji

Ten plik README zawiera instrukcje krok po kroku dotyczące budowy, uruchamiania aplikacji oraz testów.

## Budowa aplikacji bez testów

Jeśli chcesz zbudować aplikację bez uruchamiania testów, postępuj według poniższych kroków:

1. **Instalacja zależności:** Upewnij się, że masz zainstalowane wszystkie niezbędne zależności, wymagane do uruchomienia aplikacji:
*JDK 11, dostęp do internetu.*


2. **Kompilacja kodu:** Uruchom następującą komendę, aby skompilować kod źródłowy aplikacji:
*mvn clean install -DskipTests*


3. **Uruchamianie aplikacji:** Po skompilowaniu aplikacji, uruchom ją poleceniem:
*java -jar (ścieżka do zbudowanej aplikacji jar)\reimbursement-1.0.jar*
na ekranie konsoli powinien pokazać się komunikat **SERVER WORKING**
## Uruchomienie testów

Jeśli chcesz uruchomić testy, możesz to zrobić za pomocą polecenia:
*mvn test*
