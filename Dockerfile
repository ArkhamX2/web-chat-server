# Деплой приложения.
FROM eclipse-temurin:17-jre-jammy

# Рабочая дирректория.
WORKDIR /app

# Копирование скомпилированных файлов проекта.
# Перед сборкой образа необходимо подготовить билд приложения.
# TODO: Сборка приложения внутри контейнера слишком ресурсозатратна. Найти способ автоматизации.
COPY build/libs/*.jar ./web-chat-server.jar

# Переменные окружения для настройки подключения к базе данных.
ENV DATABASE_HOST="localhost"
ENV DATABASE_PORT="3306"
ENV DATABASE_USER="arkham"
ENV DATABASE_PASSWORD="secret"

# Переменная окружения параметров запуска Java для удаленной отладки.
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# Открытие портов 8080 (tomcat) и 5005 (debugger).
EXPOSE 8080 5005

# Запуск. Используется встроенный сервер tomcat.
ENTRYPOINT ["java", "-jar", "web-chat-server.jar"]
