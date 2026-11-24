# GameStore (ГЗ 1.4 - тестування та обробка помилок)

Проєкт магазину ігор на Spring Boot 3.4.13 (Java 21).

## Запуск тестів

```bash
cd gamestore-gz14
JAVA_HOME=$(/usr/libexec/java_home -v 21) ./mvnw test
```

Перевірка автономності (випадковий порядок методів):

```bash
JAVA_HOME=$(/usr/libexec/java_home -v 21) ./mvnw test -Djunit.jupiter.testmethod.order.default=org.junit.jupiter.api.MethodOrderer\$Random
```

## Покриті сценарії

1. Модульні тести (`GameMapperTest`, `GameServiceImplTest`):
   - мапінг `toCard`, `toDetail`, `toDomain`;
   - пошук усіх ігор, фільтри по `genre`, `format` та їх комбінація (`mock(GameRepository.class)`, `when/thenReturn`, `verify`);
   - `findById` для існуючого id і виняток `GameNotFoundException` для відсутнього;
   - `create`, `update`, `delete` та перевірка помилок для неіснуючого id.

2. Інтеграційний slice-тест веб-шару (`GameControllerTest` з `@WebMvcTest` і `MockMvc`):
   - `GET /api/games` і передача фільтрів `?genre=...&format=...`;
   - `GET /api/games/{id}` (200) та `404` зі структурованим JSON `ErrorResponse`;
   - `POST /api/games` (201) та `400 Bad Request` з `details` при порушенні валідації `@Valid`;
   - `PUT /api/games/{id}` (200 / 404) та `DELETE /api/games/{id}` (204 / 404).

3. Наскрізний інтеграційний тест (`GameApiIntegrationTest` з `@SpringBootTest`):
   - очищення сховища у `@BeforeEach` (`deleteAll()`), автономна підготовка даних через API;
   - повний цикл `POST -> GET -> PUT -> DELETE -> 404`, фільтрація та реальна валідація з `GlobalExceptionHandler`.
