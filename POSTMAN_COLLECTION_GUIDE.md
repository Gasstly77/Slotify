# Руководство по использованию Postman коллекции Slotify API

## Установка

1. Импортируйте файл `Slotify_API.postman_collection.json` в Postman
2. Создайте Environment в Postman со следующими переменными:
   - `baseUrl`: `http://localhost:8080` (или ваш URL сервера)
   - `accessToken`: (будет установлен автоматически после логина)
   - `refreshToken`: (будет установлен автоматически после логина)

## Аутентификация

### Шаг 1: Регистрация или Вход

1. Используйте запрос **Authentication > Register** для создания нового пользователя
2. Или используйте **Authentication > Login** для входа существующего пользователя

### Шаг 2: Автоматическое сохранение токенов

Запрос **Login** автоматически сохраняет `accessToken` и `refreshToken` в переменные окружения.

### Шаг 3: Использование токенов

Все защищенные эндпоинты используют Bearer токен из переменной `{{accessToken}}`.

## Значения Enum

### Request.Status
- `PENDING` - Ожидает рассмотрения
- `ACCEPTED` - Принята
- `REJECTED` - Отклонена
- `NEEDS_CHANGE` - Требует изменений

### Meeting.Status
- `PLANNED` - Запланировано
- `CONFIRMED` - Подтверждено
- `CANCELLED` - Отменено

### Notification.Type
- `INFO` - Информационное
- `WARNING` - Предупреждение
- `CONFIRMATION` - Подтверждение

### User.Role
- `USER` - Обычный пользователь
- `ADMIN` - Администратор

## Структура запросов

### Authentication

#### POST /api/auth/register
Регистрация нового пользователя
```json
{
    "username": "testuser",
    "password": "password123",
    "email": "testuser@example.com"
}
```

#### POST /api/auth/login
Вход в систему
```json
{
    "username": "admin",
    "password": "admin123"
}
```

#### POST /api/auth/refresh
Обновление токена
```json
{
    "refreshToken": "{{refreshToken}}"
}
```

#### POST /api/auth/logout
Выход из системы
```json
{
    "refreshToken": "{{refreshToken}}"
}
```

### Users

#### GET /api/users
Получить всех пользователей (с пагинацией)
Query параметры:
- `page`: номер страницы (по умолчанию 0)
- `size`: размер страницы (по умолчанию 20)
- `sort`: поле сортировки (по умолчанию userId)

#### GET /api/users/{id}
Получить пользователя по ID

#### POST /api/users
Создать пользователя
```json
{
    "username": "newuser",
    "passwordHash": "hashedpassword123",
    "email": "newuser@example.com",
    "role": "USER"
}
```

#### PUT /api/users/{id}
Обновить пользователя
```json
{
    "username": "updateduser",
    "passwordHash": "newhashedpassword123",
    "email": "updateduser@example.com",
    "role": "ADMIN"
}
```

#### DELETE /api/users/{id}
Удалить пользователя

### Meetings

#### GET /api/meetings
Получить все мероприятия (с пагинацией)

#### GET /api/meetings/{id}
Получить мероприятие по ID

#### POST /api/meetings (ADMIN only)
Создать мероприятие
```json
{
    "title": "Встреча команды разработки",
    "description": "Обсуждение текущих задач и планов",
    "finalTime": "2024-12-31T15:00:00",
    "status": "PLANNED",
    "meetingTypeId": 1,
    "locationId": 1
}
```

#### PUT /api/meetings/{id} (ADMIN only)
Обновить мероприятие

#### DELETE /api/meetings/{id} (ADMIN only)
Удалить мероприятие

### Requests

#### GET /api/requests (ADMIN only)
Получить все запросы (с пагинацией)

#### GET /api/requests/{id}
Получить запрос по ID

#### POST /api/requests (USER only)
Создать запрос на участие
```json
{
    "userId": 1,
    "slotId": 1,
    "participationTypeId": 1,
    "status": "PENDING"
}
```
**Примечание:** `userId` устанавливается автоматически из токена текущего пользователя

#### PUT /api/requests/{id}
Обновить запрос

#### DELETE /api/requests/{id}
Удалить запрос

### Time Slots

#### GET /api/time-slots
Получить все временные слоты

#### GET /api/time-slots/{id}
Получить временной слот по ID

#### POST /api/time-slots (ADMIN only)
Создать временной слот
```json
{
    "meetingId": 1,
    "startTime": "2024-12-31T10:00:00",
    "endTime": "2024-12-31T11:00:00",
    "isFinal": false
}
```

#### PUT /api/time-slots/{id} (ADMIN only)
Обновить временной слот

#### DELETE /api/time-slots/{id} (ADMIN only)
Удалить временной слот

### Locations

#### GET /api/locations
Получить все локации

#### GET /api/locations/{id}
Получить локацию по ID

#### POST /api/locations (ADMIN only)
Создать локацию
```json
{
    "name": "Конференц-зал А",
    "details": "Первый этаж, комната 101, вместимость 50 человек"
}
```

#### PUT /api/locations/{id} (ADMIN only)
Обновить локацию

#### DELETE /api/locations/{id} (ADMIN only)
Удалить локацию

### Meeting Types

#### GET /api/meeting-types
Получить все типы мероприятий

#### GET /api/meeting-types/{id}
Получить тип мероприятия по ID

#### POST /api/meeting-types (ADMIN only)
Создать тип мероприятия
```json
{
    "name": "Совещание",
    "description": "Внутренние совещания команды"
}
```

#### PUT /api/meeting-types/{id} (ADMIN only)
Обновить тип мероприятия

#### DELETE /api/meeting-types/{id} (ADMIN only)
Удалить тип мероприятия

### Participation Types

#### GET /api/participation-types
Получить все типы участия

#### GET /api/participation-types/{id}
Получить тип участия по ID

#### POST /api/participation-types (ADMIN only)
Создать тип участия
```json
{
    "name": "Онлайн",
    "description": "Участие через видеосвязь"
}
```

#### PUT /api/participation-types/{id} (ADMIN only)
Обновить тип участия

#### DELETE /api/participation-types/{id} (ADMIN only)
Удалить тип участия

### Notifications

#### GET /api/notifications/my (USER only)
Получить все уведомления текущего пользователя

#### GET /api/notifications (ADMIN only)
Получить все уведомления

#### GET /api/notifications/{id}
Получить уведомление по ID

#### POST /api/notifications
Создать уведомление
```json
{
    "userId": 1,
    "message": "Ваша заявка была принята",
    "type": "CONFIRMATION",
    "isRead": false
}
```

#### PUT /api/notifications/{id}
Обновить уведомление

#### DELETE /api/notifications/{id}
Удалить уведомление

### Admin Operations

#### GET /api/admin/meetings/{meetingId}/requests (ADMIN only)
Получить все заявки на указанное мероприятие (с пагинацией)

#### POST /api/admin/requests/accept-or-reject (ADMIN only)
Принять или отклонить заявку
```json
{
    "requestId": 1,
    "accept": true
}
```
- `accept: true` - принять заявку
- `accept: false` - отклонить заявку

#### POST /api/admin/time-slots/set-final-time (ADMIN only)
Установить финальное время для временного слота
```json
{
    "timeSlotId": 1,
    "finalTime": "2024-12-31T15:00:00"
}
```

#### GET /api/admin/meetings/{meetingId}/time-slots (ADMIN only)
Получить все временные слоты для мероприятия

### User Operations

#### GET /api/user/my-requests (USER only)
Получить все заявки текущего пользователя (с пагинацией)

#### GET /api/user/my-accepted-meetings (USER only)
Получить все принятые заявки текущего пользователя

#### GET /api/user/active-meetings (USER only)
Получить все активные мероприятия доступные для участия (с пагинацией)

## Права доступа

- **Публичные эндпоинты**: Регистрация, Вход
- **USER**: Все эндпоинты пользователя, создание запросов, просмотр своих уведомлений
- **ADMIN**: Все эндпоинты, включая создание/обновление/удаление мероприятий, временных слотов, локаций, типов и административные операции

## Формат даты и времени

Все даты и времена должны быть в формате ISO 8601:
- `2024-12-31T15:00:00` (без часового пояса)
- `2024-12-31T15:00:00Z` (UTC)

## Примеры использования

### Сценарий 1: Регистрация и создание запроса

1. **POST /api/auth/register** - Зарегистрировать нового пользователя
2. **GET /api/user/active-meetings** - Получить активные мероприятия
3. **GET /api/time-slots** - Получить доступные временные слоты
4. **GET /api/participation-types** - Получить типы участия
5. **POST /api/requests** - Создать запрос на участие

### Сценарий 2: Администратор создает мероприятие

1. **POST /api/auth/login** - Войти как администратор
2. **POST /api/locations** - Создать локацию (если нужно)
3. **POST /api/meeting-types** - Создать тип мероприятия (если нужно)
4. **POST /api/meetings** - Создать мероприятие
5. **POST /api/time-slots** - Создать временные слоты для мероприятия

### Сценарий 3: Администратор обрабатывает заявки

1. **GET /api/admin/meetings/{meetingId}/requests** - Получить заявки на мероприятие
2. **POST /api/admin/requests/accept-or-reject** - Принять/отклонить заявки
3. **POST /api/admin/time-slots/set-final-time** - Установить финальное время

## Примечания

- Все ID в примерах должны быть заменены на реальные ID из вашей базы данных
- После логина токены автоматически сохраняются в переменные окружения
- Для обновления токена используйте запрос **Refresh Token**
- При работе с пагинацией используйте параметры `page`, `size` и `sort`

