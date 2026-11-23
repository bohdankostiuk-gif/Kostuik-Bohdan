# Запити для перевірки API (ПЗ 1.3 - CRUD, фільтри та валідація)

Запуск сервера (потрібна Java 21):

```bash
cd gamestore-pz13
./mvnw spring-boot:run
```

## 1. Список усіх ігор

```bash
curl http://localhost:8080/api/games
```

Повертає `200 OK` і масив карток (`id`, `title`, `platform`, `price`, `posterUrl`).

## 2. Фільтри за жанром та форматом

```bash
curl "http://localhost:8080/api/games?genre=RPG"
```

```bash
curl "http://localhost:8080/api/games?format=Disc"
```

```bash
curl "http://localhost:8080/api/games?genre=RPG&format=Disc"
```

## 3. Одна гра за id

```bash
curl http://localhost:8080/api/games/1
```

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
    "format": "Digital",
    "releaseYear": 2020,
    "price": 515.00,
    "posterUrl": "/posters/hades.jpg",
    "description": "Екшн-рогалик про втечу з підземного світу за мотивами грецької міфології.",
    "ageRating": "12+"
  }'
```

## 5. Помилка валідації (400 Bad Request)

Порожня назва та від'ємна ціна не проходять `@NotBlank` / `@Positive`:

```bash
curl -i -X POST http://localhost:8080/api/games \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "studio": "Supergiant Games",
    "platform": "PC",
    "genre": "Roguelike",
    "format": "Digital",
    "releaseYear": 2020,
    "price": -100.00,
    "posterUrl": "/posters/hades.jpg",
    "description": "Опис",
    "ageRating": "12+"
  }'
```

Повертає `400 Bad Request`.

## 6. Оновити гру (200 OK / 404 Not Found)

```bash
curl -i -X PUT http://localhost:8080/api/games/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "S.T.A.L.K.E.R. 2: Heart of Chornobyl (Deluxe)",
    "studio": "GSC Game World",
    "platform": "PC / Xbox Series X",
    "genre": "Шутер",
    "format": "Digital",
    "releaseYear": 2024,
    "price": 1899.00,
    "posterUrl": "/posters/stalker2.jpg",
    "description": "Розширене видання шутера у відкритому світі Чорнобильської зони.",
    "ageRating": "18+"
  }'
```

Неіснуючий id повертає `404 Not Found`:

```bash
curl -i -X PUT http://localhost:8080/api/games/999 \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","studio":"Studio","platform":"PC","genre":"RPG","format":"Digital","releaseYear":2024,"price":100.00,"posterUrl":"/posters/test.jpg","description":"Опис","ageRating":"12+"}'
```

## 7. Видалити гру (204 No Content / 404 Not Found)

```bash
curl -i -X DELETE http://localhost:8080/api/games/2
```

Неіснуючий id повертає `404 Not Found`:

```bash
curl -i -X DELETE http://localhost:8080/api/games/999
```
