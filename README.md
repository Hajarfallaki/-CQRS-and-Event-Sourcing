# TP : Micro-service CQRS/Event Sourcing avec Spring Boot & Axon

## 📝 Description du Projet

Ce projet implémente un micro-service bancaire de gestion de comptes en utilisant les patterns de conception **Command Query Responsibility Segregation (CQRS)** et **Event Sourcing (ES)**.

* **Command Side** : Utilise les agrégats Axon pour traiter les commandes (`CreateAccountCommand`, `CreditAccountCommand`, `DebitAccountCommand`) et persiste les changements sous forme d'événements dans l'Event Store (Axon Server).
* **Query Side** : Utilise des `EventHandler` pour mettre à jour une vue matérialisée des comptes (projection) stockée dans une base de données relationnelle (H2).
* **Communication** : Axon Framework gère le routage des commandes/requêtes et la distribution des événements via Axon Server.

---

## 🛠️ Prérequis

Pour lancer et tester l'application, les éléments suivants sont nécessaires :

* JDK : Version 17+
* Maven
* Axon Server Standard Edition (axonserver.jar)
* IDE : IntelliJ IDEA (recommandé) ou Eclipse

---

## 🏗️ Structure du Projet (CQRS & DDD)

Le projet est organisé autour du package racine `ma.enset.java.demoescqrsaxon`, séparant clairement les responsabilités Commandes et Requêtes.

### 1. 🟢 Command Side (Côté Écriture)

| Package              | Rôle                                          | Classes Clés                               |
| -------------------- | --------------------------------------------- | ------------------------------------------ |
| commands             | Intentions de changement d'état               | CreateAccountCommand, CreditAccountCommand |
| events               | Les faits immuables déclenchés après commande | AccountCreatedEvent, AccountCreditedEvent  |
| aggregates           | Logique centrale, gestion d'état              | AccountAggregate                           |
| commands.controllers | API REST pour envoyer les commandes           | AccountCommandController                   |
| dtos                 | Transport des données                         | AddNewAccountRequestDTO                    |

### 2. 🔵 Query Side (Côté Lecture / Projection)

| Package            | Rôle                                   | Classes Clés                             |
| ------------------ | -------------------------------------- | ---------------------------------------- |
| queries            | Lecture de l'état des comptes          | GetAccountByIdQuery, GetAllAccountsQuery |
| query.entities     | Entités JPA (projection)               | Account                                  |
| query.repositories | Repositories JPA                       | AccountRepository                        |
| query.services     | Contains @EventHandler & @QueryHandler | AccountQueryHandler                      |

---

## ⚙️ Configuration du Projet

### 1. Dépendances Clés (pom.xml)

* axon-spring-boot-starter
* spring-boot-starter-data-jpa
* h2
* springdoc-openapi-starter-webmvc-ui
* lombok

### 2. Configuration application.properties

```
spring.application.name=demo-es-cqrs-axon

spring.datasource.url=jdbc:h2:mem:demo_es_cqrs_db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

server.port=8081

axon.axonserver.servers=localhost:8124
```

---

## 💻 Démarrage du Projet

### Étape 1 : Démarrer Axon Server

```
java -jar axonserver.jar
```

Interface de contrôle : [http://localhost:8024](http://localhost:8024)

### Étape 2 : Démarrer l'application Spring Boot

Lancer : `DemoEsCqrsAxonApplication`

💡 Vérification :

* API fonctionnelle sur [http://localhost:8081](http://localhost:8081)
* Axon Console montre l'application connectée

---

## 🧪 Tests Fonctionnels (Swagger UI)

Endpoint Swagger : [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

| Action             | Endpoint                         | Résultat     |
| ------------------ | -------------------------------- | ------------ |
| Créer un compte    | `POST /commands/accounts/create` | solde = 1500 |
| Créditer 500 MAD   | `PUT /commands/accounts/credit`  | solde = 2000 |
| Lire solde (Query) | `GET /query/accounts/byId/{id}`  | solde = 2000 |
| Débiter 200 MAD    | `PUT /commands/accounts/debit`   | solde = 1800 |
| Lire solde final   | `GET /query/accounts/byId/{id}`  | solde = 1800 |

---

## 📊 Visualisation des Événements (Axon Console)

Console web : [http://localhost:8024](http://localhost:8024)

Dans *Event Store*, vous pouvez consulter :

* la liste des agrégats
* l'historique complet des événements par compte

Cela permet d'observer l’Event Sourcing en action.

---

## 📌 Conclusion

Ce projet démontre efficacement l’implémentation réelle des patterns CQRS & Event Sourcing avec Axon et Spring Boot.
Il permet de comprendre :

* séparation lecture/écriture
* stockage événementiel du changement d’état
* reconstruction d’état depuis les événements

Ce modèle est idéal pour les systèmes hautement scalables, audités et évolutifs.
