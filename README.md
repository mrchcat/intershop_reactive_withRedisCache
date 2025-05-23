# Internet shop prototype

Сервис представляет собой фронтэнд и бэкэнд интернет-магазина, а также прототип платежного сервиса. 
Реализован вывод списка товаров, пагинация, поиск, сортировка, корзина заказов, страница с перечнем совершенных ранее заказов,
покупка товаров через внешний платежный сервис.
На данном этапе не поддерживается в полной мере многопользовательский режим, пользователи задаются через файл конфигурации.
Бэкенд построен на реактивном стеке. Поддерживается кеширование товаров через БД Redis.

Версия: Java 21

Зависимости: Spring WebFlux, Spring Data, Thymeleaf, Netty, Redis, Postgres, Maven, JUnit, Lombok, Testcontainers

Для запуска программы необходим Docker.
1) Перейдите в папку /intershop и выполните команду "mvnw clean package". Дождитесь сборки приложения.
2) Выполните команду "docker-compose up". 
3) После запуска контейнеров магазин будет доступен по адресу http://localhost:8080
   В случае конфликта портов внесите исправления в docker-compose.yaml.

Настройки в файле application.yaml:
* application.user_id - активирует корзину пользователя c заданным id
* application.items.perline - количество товаров, отображаемых в одном ряду
* application.items.load.enabled - включение возможности загрузки картинок
* application.items.load.images.directory: директория для хранения загруженных картинок
* shop.payment_id: идентификатор магазина внутри платежной системы

Загрузка картинок:
* после запуска приложения по адресу "/admin/items/download" доступна загрузка картинок в базу данных при условии, что
application.items.load.enabled установлен в true и задан каталог для хранения статических изображений.

Схема базы данных основного приложения:
![](https://github.com/mrchcat/intershop_reactive_withRedisCache/blob/main/shop/src/main/resources/schema.png)

Платежный сервис реализует следующий API:
[спецификация OpenApi](https://github.com/mrchcat/intershop_reactive_withRedisCache/blob/main/payservice/PayServiceOpenApi.yaml)

Схема базы данных платежного сервиса:
![](https://github.com/mrchcat/intershop_reactive_withRedisCache/blob/main/payservice/server/src/main/resources/schema.png)

Загрузка исходных данных как в БД магазина, так и в платежный сервис осуществляется скриптом при загрузке приложения. 
