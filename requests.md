# Запити для перевірки API (завдання 1.1)

Запуск сервера (потрібна Java 21):

```bash
./mvnw spring-boot:run
```

## Отримати список усіх ігор

```bash
curl http://localhost:8080/api/games
```

Повертає `200 OK` і масив карток (`id`, `title`, `platform`, `price`, `posterUrl`).

## Отримати одну гру за id

```bash
curl http://localhost:8080/api/games/1
```

Повертає `200 OK` і повну інформацію про гру (`studio`, `genre`, `releaseYear`, `description`, `ageRating` тощо).

## Запит за неіснуючим id

```bash
curl -i http://localhost:8080/api/games/999
```

Повертає `404 Not Found`.
