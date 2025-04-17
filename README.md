# Hệ thống giám sát và điều chỉnh môi trường nhà kính

### Ngôn ngữ và công nghệ
- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) **Ngôn ngữ:** Java
- ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) **Cơ sở dữ liệu:** MySQL
- ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) **Công nghệ:** Spring Boot
### Hướng dẫn cài đặt
Các úng dụng cần cài đặt trước khi chạy ứng dụng:
- ![Java](https://www.oracle.com/java/technologies/downloads/)
- ![Apache maven](https://maven.apache.org/)
- ![MySQL](https://www.mysql.com/)

## Thiết lập database
```yaml
spring:
  datasource:
    url: ${YOUR_DB_URL}
    username: ${YOUR_DB_USERNAME}
    password: ${YOUR_DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## Thiết lập MQTT
```yaml
mqtt:
  brokerUrl: "tcp://io.adafruit.com:1883"
  username: ${YOUR_USERNAME}
  apiKey: ${YOUR_MQTT_API_KEY}
  clientId: ${YOUR_CLIENT_ID}
```

## Lệnh cần chạy
```bash
git clone https://github.com/KienLe21/BE_GreenHouse.git
mvn spring-boot:run
```
