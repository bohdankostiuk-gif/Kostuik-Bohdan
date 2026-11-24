# Запити для перевірки API (ГЗ 1.4 - тести та обробка помилок)

Запуск сервера (потрібна Java 21):

```bash
cd gamestore-gz14
./mvnw spring-boot:run
```

## 1. Список ігор та фільтри

```bash
curl http://localhost:8080/api/games
```

```bash
curl "http://localhost:8080/api/games?genre=RPG&format=Disc"
```

## 2. Неіснуючий id -> 404 зі структурованим тілом

```bash
curl -i http://localhost:8080/api/games/999
```

Очікувано `HTTP/1.1 404` і JSON:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Гру з id=999 не знайдено",
  "details": []
}
```

## 3. Невалідні дані -> 400 з переліком помилок

```bash
curl -i -X POST http://localhost:8080/api/games \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "studio": "",
    "genre": "",
    "price": -50
  }'
```

Очікувано `HTTP/1.1 400` і JSON:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Помилка валідації вхідних даних",
  "details": [
    "title: назва обов'язкова",
    "studio: студія обов'язкова",
    "genre: жанр обов'язковий",
    "price: ціна має бути додатною"
  ]
}
```

## 4. Валідне створення, оновлення та видалення

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

```bash
curl -i -X DELETE http://localhost:8080/api/games/2
```
