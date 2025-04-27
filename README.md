# Bank card

## Описание проекта

Приложение для управления, банковскими картами. Приложение состоит из двух основных компонентов: бекенда на Java и базы
данных PostgreSQL.

## Основные компоненты

- **Backend:** Java приложение для обработки запросов и взаимодействия с базой данных.
- **Database:** PostgreSQL для хранения данных о картах и пользователях.

## Требования

- Docker
- Docker Compose

## Запуск приложения

### Локальный запуск

1. Клонируйте репозиторий:

    ```bash
    git clone https://github.com/AVAndrianov/BankCard.git
    cd BankCard
    ```
   
2. Перейдите в ветку мастер:

   ```bash
   git checkout master
   ``` 

3. Обновление переменной среды чтобы она указывала на Java 17:
  
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home)
   ```  
   
4. Запустите контейнеры:

    ```bash
    docker compose up
    ```

5. Откройте браузер и перейдите по адресу:

    - Backend: `http://localhost:8080`
    - Swagger: `http://localhost:8080/swagger-ui/index.html`

## API эндпоинты

### Пользователи

- **POST /auth/sign-up:** Регистрация нового пользователя.
- **POST /auth/sign-in:** Авторизация пользователя.
- **GET /get-admin:** Получить права администратора
-

### Задачи

- **GET /addCard/:number:balance:owner:** Добавить карту (требуется аутентификация).
- **GET /getAllCardAdmin/:size:** Получить список всех карт (требуется аутентификация).
- **GET /setDayLimitCardSetting/:id:** Получение информации о конкретной задаче (требуется аутентификация).

PostmanCollection - в корне проекта
