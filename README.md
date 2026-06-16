# EduMicro — Système d'inscription

Application web de gestion des inscriptions universitaires, développée en **architecture microservices**.

Le frontend communique avec un **API Gateway**, qui redirige les requêtes vers les microservices métier. Chaque service possède sa propre base **MySQL**.

La découverte des services est assurée par **Netflix Eureka** : les microservices s'enregistrent dans un annuaire central et communiquent via des **noms logiques** (`student-service`, `course-service`, etc.) résolus par le **LoadBalancer** Spring Cloud.

## Fonctionnalités

- Gestion des **étudiants** (CRUD)
- Gestion des **cours**
- **Inscription** d'un étudiant à un cours
- Consultation des inscriptions par CNIE
- Suppression d'une inscription dans un délai de **24 heures**
- Limite de **3 étudiants par cours**

## Architecture

```
Frontend (React CSR) :5500
         ↓ HTTP / JSON
API Gateway :8080  (CORS, routage lb://)
         ↓
    Eureka Server :8761  (annuaire des services)
         ↓
┌─────────────────┬─────────────────┬──────────────────────┐
│ student-service │ course-service  │ enrollment-service   │
│     :8081       │     :8082       │        :8083         │
│   student_db    │   course_db     │    enrollment_db     │
└─────────────────┴─────────────────┴──────────────────────┘
                              ↓ WebClient (@LoadBalanced)
                    appelle student + course via Eureka
```

| Composant | Rôle |
|-----------|------|
| **Eureka Server** | Registre central — chaque service s'y enregistre au démarrage |
| **Eureka Client** | Présent sur gateway + les 3 microservices |
| **LoadBalancer** | Traduit `lb://student-service` ou `http://student-service` en adresse réelle |
| **API Gateway** | Point d'entrée unique du front (`lb://` dans les routes) |
| **WebClient** | Communication interne enrollment → student / course |

## Technologies

| Couche | Technologies |
|--------|--------------|
| Backend | Java 17, Spring Boot, Spring Cloud Gateway, Spring Data JPA, Hibernate, WebClient |
| Service discovery | Netflix Eureka, Spring Cloud LoadBalancer |
| Frontend | React 18, JavaScript, Tailwind CSS, Fetch API (CSR) |
| Base de données | MySQL |
| Build | Maven |

## Structure du projet

```
projet-microservices/
├── eureka-server/        # Annuaire des services (port 8761)
├── api-gateway/          # Point d'entrée unique (port 8080)
├── student-service/      # Gestion des étudiants (port 8081)
├── course-service/       # Gestion des cours (port 8082)
├── enrollment-service/   # Gestion des inscriptions (port 8083)
└── frontend/             # Interface React (index.html)
```

## Prérequis

- **Java 17**
- **Maven**
- **MySQL** (port `3307` dans la configuration actuelle)
- Un serveur local pour le frontend (Live Server, VS Code, etc.)

## Configuration MySQL

Créer les bases de données :

```sql
CREATE DATABASE student_db;
CREATE DATABASE course_db;
CREATE DATABASE enrollment_db;
```

Identifiants par défaut dans les fichiers `application.properties` :

- **Utilisateur** : `root`
- **Mot de passe** : `root`
- **Port** : `3307`

> Hibernate crée automatiquement les tables (`spring.jpa.hibernate.ddl-auto=update`).

## Lancement du projet

Démarrer les services **dans cet ordre** :

### 1. MySQL

Vérifier que MySQL écoute sur le port `3307`.

### 2. Eureka Server

```bash
cd eureka-server
./mvnw spring-boot:run
```

Dashboard Eureka : `http://localhost:8761`

### 3. Microservices

Dans chaque dossier de service :

```bash
# student-service
cd student-service
./mvnw spring-boot:run

# course-service
cd course-service
./mvnw spring-boot:run

# enrollment-service
cd enrollment-service
./mvnw spring-boot:run

# api-gateway
cd api-gateway
./mvnw spring-boot:run
```

Sous Windows, utiliser `mvnw.cmd` à la place de `./mvnw`.

> Vérifier sur le dashboard Eureka que les 4 services sont enregistrés avant de tester le front.

### 4. Frontend

Ouvrir `frontend/index.html` avec Live Server ou un serveur local.

- Frontend : `http://localhost:5500` (ou port affiché par Live Server)
- API Gateway : `http://localhost:8080/api`
- Eureka : `http://localhost:8761`

## Ports

| Service | Port |
|---------|------|
| Eureka Server | 8761 |
| API Gateway | 8080 |
| student-service | 8081 |
| course-service | 8082 |
| enrollment-service | 8083 |
| Frontend | 5500 (Live Server) |
| MySQL | 3307 |

## API principales

Toutes les requêtes passent par le gateway : `http://localhost:8080/api`

### Étudiants

| Méthode | Endpoint |
|---------|----------|
| GET | `/students` |
| GET | `/students/{id}` |
| GET | `/students/cnie/{cnie}` |
| POST | `/students` |
| DELETE | `/students/{id}` |

### Cours

| Méthode | Endpoint |
|---------|----------|
| GET | `/courses` |
| GET | `/courses/{id}` |
| POST | `/courses` |

### Inscriptions

| Méthode | Endpoint |
|---------|----------|
| POST | `/enrollments` |
| GET | `/enrollments/count` |
| GET | `/enrollments/student/{cnie}` |
| DELETE | `/enrollments/{id}` |

## Règles métier

- Un étudiant ne peut pas être inscrit deux fois au même cours
- Maximum **3 étudiants** par cours
- Une inscription est **supprimable** seulement dans les **24 heures** suivant la date d'inscription

## Auteur

**Safaa Bifkioun**
