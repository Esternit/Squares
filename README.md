# 🟦 Игра в квадраты

Игра для двух игроков (человек vs компьютер), где цель — первым построить квадрат из фишек своего цвета на поле N×N.  
Фишки могут образовывать квадрат в любом повороте.

---

## Структура проекта

- **`/Engine`** — Spring Boot бэкенд (REST API + логика игры)
- **`/web`** — Next.js фронтенд (React + shadcn/ui)

---

## Быстрый запуск с Docker

Убедитесь, что установлены [Docker](https://www.docker.com/) и [Docker Compose](https://docs.docker.com/compose/).

```bash
# Сборка и запуск
docker-compose up --build
```

После запуска:
- 🖥️ Фронтенд: [http://localhost:3000](http://localhost:3000)
- 📡 Бэкенд API: [http://localhost:8080](http://localhost:8080) (Swagger UI доступен)

> Консольную версию **нельзя запустить через Docker** — только вручную (см. ниже).

---

## Ручной запуск (без Docker)

### Требования
- Java 21+
- Maven 3.9+
- Node.js 20+
- npm

---

### 1. Запуск бэкенда

```bash
cd Engine
mvn spring-boot:run
```

Бэкенд будет доступен на `http://localhost:8080`.

---

### 2. Запуск фронтенда

```bash
cd web
npm install
npm run dev
```

Фронтенд откроется на `http://localhost:3000`.

> Убедитесь, что бэкенд уже запущен — фронтенд обращается к нему напрямую в режиме разработки.

---

### 3. Запуск консольной версии

Консольная версия **не входит в веб-интерфейс** и запускается отдельно:

```bash
cd Engine
mvn clean install
java -jar target/square-game-1.0-console.jar
```
---

## Сборка проекта

### Бэкенд
```bash
cd Engine
mvn clean install
```
→ Генерирует:
- `square-game-1.0-web.jar` — веб-версия
- `square-game-1.0-console.jar` — консольная версия

### Фронтенд
```bash
cd web
npm run build
```

---

## Тестирование

Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Примечания

- В режиме разработки фронтенд использует прямые запросы к `http://localhost:8080/api`.
- В Docker-сборке фронтенд проксирует запросы через Next.js к сервису `backend`.
- Консольная версия доступна **только через Maven или JAR**, не через веб-интерфейс.
