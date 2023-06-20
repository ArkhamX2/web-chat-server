FROM eclipse-temurin:17-jre-jammy

# Копирование скомпилированных файлов проекта
# Перед сборкой образа необходимо подготовить билд приложения
# TODO: Сборка приложения внутри контейнера слишком ресурсозатратна. Найти способ автоматизации.
COPY build/libs/*.jar /app/web-chat-server.jar

# Переменные окружения
ENV DATABASE_HOST="localhost"
ENV DATABASE_PORT="3306"
ENV DATABASE_USER="arkham"
ENV DATABASE_PASSWORD="secret"

# Переменная окружения параметров запуска Java для удаленной отладки
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# Открытие портов 8080 и 5005
EXPOSE 8080 5005

WORKDIR /app

ENTRYPOINT ["java", "-jar", "web-chat-server.jar"]
