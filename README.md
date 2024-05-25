#Management Google Events

This is a Spring Boot application that allows users to manage Google Calendar events.

## Features

- OAuth2 authentication with Google.
- Create, update, and delete Google Calendar Tasks.
- Integration with Google Calendar Api to create Event
- Pagination support for fetching Tasks.


## Technologies

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Security with OAuth2
- Google Calendar API
- MySQL
- Maven

## Setup

1. Clone the repository.
2. Update the `src/main/resources/application.properties` file with your MySQL credentials and Google Client ID and Secret.
3. Run the application using the command `mvn spring-boot:run`.

## API Endpoints

- `POST /v1/tasks`: Create a new task.
- `GET /v1/tasks/{id}`: Fetch a task by ID.
- `GET /v1/tasks`: Fetch all tasks.
- `GET /v1/tasks/page`: Fetch tasks with pagination.
- `PUT /v1/tasks/{id}`: Update a task.
- `PATCH /v1/tasks/{id}`: Partially update a task.
- `DELETE /v1/tasks/{id}`: Delete a task.
- `DELETE /v1/tasks`: Delete all tasks.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
