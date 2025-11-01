# 🌿 EcoTrack - Environmental Action Tracker

EcoTrack is a Spring Boot web application that allows users to track their environmental actions and compete with others in making a positive impact on the environment.

## Features

- 👤 User Authentication (Register, Login, Logout)
- ✨ Add/Edit/Delete Eco Actions
- 📊 Personal Dashboard
- 🏆 Leaderboard
- 🎖️ Achievement Badges
- 📸 Image Upload Support
- 👨‍💼 Admin Panel

## Technologies Used

- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database
- Thymeleaf
- Bootstrap 5
- Lombok

## Prerequisites

- Java 17 or higher
- Maven

## Setup and Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/ecotrack.git
cd ecotrack
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application at http://localhost:8080

## Project Structure

```
src/main/java/com/ecotrack/
├── controller/
│   ├── AdminController.java
│   ├── EcoActionController.java
│   └── UserController.java
├── model/
│   ├── User.java
│   ├── EcoAction.java
│   └── Badge.java
├── repository/
│   ├── UserRepository.java
│   ├── EcoActionRepository.java
│   └── BadgeRepository.java
├── service/
│   ├── UserService.java
│   └── EcoActionService.java
└── EcoTrackApplication.java
```

## Database Configuration

The application uses H2 in-memory database by default. The database console is available at http://localhost:8080/h2-console with the following default credentials:
- JDBC URL: jdbc:h2:file:./ecotrack-db
- Username: sa
- Password: (empty)

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request