# HotelREST
RESTful API для управления отелями, написанное на Java с использованием Spring Boot.

## О проекте
Этот проект реализует базовый сервис для создания, получения и управления данными отелей. Цель — показать навыки в разработке REST API, работе с базой данных и тестированию.

## Технологии
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Liquibase
- Maven

## Запуск проекта
1. Клонируй репозиторий:

    ```bash
    git clone https://github.com/NessReverdy/HotelREST.git
    cd HotelREST
    ```

2. Запусти проект командой:

    ```bash
    mvn spring-boot:run
    ```

3. Приложение запустится на http://localhost:8092

## Основные эндпоинты
- GET `/property-view/hotels` — получить список всех отелей
- GET `/property-view/hotels/{id}` — получить детальную информацию по отелю по ID
- POST `/property-view/hotels` — создать новый отель

Для подробной документации и тестирования API доступен [Swagger UI](http://localhost:8092/swagger-ui/index.html)

## Тестирование

Проект содержит юнит-тесты для сервисного слоя. Запустить тесты можно командой:
```bash
mvn test
```
