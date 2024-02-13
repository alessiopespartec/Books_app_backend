# Spring Boot Book Catalog Application

## Descrizione
Questo progetto è un'applicazione Spring Boot che fornisce API REST per gestire un catalogo di libri, autori ed editori. L'applicazione si interfaccia con un database MySQL per salvare e recuperare dati, gestendo relazioni complesse tra le varie entità.

### Sviluppo dei Controller REST
- Controller REST con endpoint per operazioni CRUD su libri, autori, editori ed utenti.

### Gestione delle Eccezioni e Validazione
- Gestione delle eccezioni implementata.
- Validazione degli input per gli endpoint.

### Sicurezza
- Implementata l'autenticazione base di Spring Security.
- All'avvio dell'applicazione, viene creato automaticamente un utente con email `admin@example.com` e password `password123`.
- Tutti gli endpoint sono protetti e richiedono l'autenticazione.

---

### Configurazione
Modifica il file `application.properties` per configurare la connessione al database MySQL.
