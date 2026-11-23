# Запити для перевірки API (завдання 1.2 — CRUD)

Запуск сервера (потрібна Java 21):

```bash
cd gamestore-gz12
./mvnw spring-boot:run
```

## 1. Список усіх ігор

```bash
curl http://localhost:8080/api/games
```

Повертає `200 OK` і масив карток (`id`, `title`, `platform`, `price`, `posterUrl`).

## 2. Фільтр за жанром

```bash
curl "http://localhost:8080/api/games?genre=RPG"
```

Повертає `200 OK` і лише ігри вказаного жанру.

## 3. Одна гра за id

```bash
curl http://localhost:8080/api/games/1
```

Повертає `200 OK` і повну інформацію про гру.

Для неіснуючого id повертає `404 Not Found`:

```bash
curl -i http://localhost:8080/api/games/999
```

## 4. Створити гру (201 Created)

```bash
curl -i -X POST http://localhost:8080/api/games \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Hades",
    "studio": "Supergiant Games",
    "platform": "PC / Switch / PS5",
    "genre": "Roguelike",
    "releaseYear": 2020,
    "price": 515.00,
    "posterUrl": "/posters/hades.jpg",
    "description": "Екшн-рогалик про втечу з підземного світу за мотивами грецької міфології.",
    "ageRating": "12+"
  }'
```

Повертає `201 Created` і JSON створеної гри з новим `id`.

## 5. Оновити гру (200 OK / 404 Not Found)

```bash
curl -i -X PUT http://localhost:8080/api/games/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "S.T.A.L.K.E.R. 2: Heart of Chornobyl (Deluxe)",
    "studio": "GSC Game World",
    "platform": "PC / Xbox Series X",
    "genre": "Шутер",
    "releaseYear": 2024,
    "price": 1899.00,
    "posterUrl": "/posters/stalker2.jpg",
    "description": "Розширене видання шутера у відкритому світі Чорнобильської зони.",
    "ageRating": "18+"
  }'
```

Оновлення неіснуючої гри повертає `404 Not Found`:

```bash
curl -i -X PUT http://localhost:8080/api/games/999 \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","studio":"Studio","platform":"PC","genre":"RPG","releaseYear":2024,"price":100.00,"posterUrl":"/posters/test.jpg","description":"Опис","ageRating":"12+"}'
```

## 6. Видалити гру (204 No Content / 404 Not Found)

```bash
curl -i -X DELETE http://localhost:8080/api/games/2
```

Повертає `204 No Content` без тіла відповіді.

Повторне видалення або неіснуючий id повертає `404 Not Found`:

```bash
curl -i -X DELETE http://localhost:8080/api/games/999
```
