# EduMicro — Système d'inscription

Application web de gestion des inscriptions universitaires, développée en **architecture microservices**.

Le frontend communique avec un **API Gateway**, qui redirige les requêtes vers les microservices métier. Chaque service possède sa propre base **MySQL**.

## Fonctionnalités

- Gestion des **étudiants** (CRUD)
- Gestion des **cours**
- **Inscription** d'un étudiant à un cours
- Consultation des inscriptions par CNIE
- Suppression d'une inscription dans un délai de **24 heures**
- Limite de **3 étudiants par cours**

## Architecture

```
Frontend (React)
      ↓
API Gateway :8080
      ↓
┌─────────────────┬─────────────────┬──────────────────────┐
│ student-service │ course-service  │ enrollment-service   │
│     :8081       │     :8082       │        :8083         │
│   student_db    │   course_db     │    enrollment_db     │
└─────────────────┴─────────────────┴──────────────────────┘
                              ↓ WebClient
                    appelle student + course
```

## Technologies

| Couche | Technologies |
|--------|--------------|
| Backend | Java 17, Spring Boot, Spring Cloud Gateway, Spring Data JPA, Hibernate, WebClient |
| Frontend | React 18, JavaScript, Tailwind CSS, Fetch API (CSR) |
| Base de données | MySQL |
| Build | Maven |

## Structure du projet

```
projet-microservices/
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

### 2. Microservices

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

### 3. Frontend

Ouvrir `frontend/index.html` avec Live Server ou un serveur local.

- Frontend : `http://localhost:5500` (ou port affiché par Live Server)
- API Gateway : `http://localhost:8080/api`

## Ports

| Service | Port |
|---------|------|
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

**Safaa Bifkion**
