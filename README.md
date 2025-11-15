# 🏫 Classroom Project

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.2.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-alpine-blue)
![Redis](https://img.shields.io/badge/Redis-Latest-orange)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.9-alpine-purple)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## 🔹 Layihə Haqqında

Bu layihə **Classroom** tipli web tətbiqidir. İstifadəçilər qeydiyyatdan keçə, login ola, text faylları yükləyə və bütün yüklənmiş faylları görə bilərlər. Layihədə həmçinin **Admin Panel**, **Redis caching**, **RabbitMQ mesajlaşması**, **PostgreSQL** və **Spring Security JWT** ilə qorunan endpointlər mövcuddur.

---

## ⚡ Texnologiyalar

- **Backend:** Java, Spring Boot  
- **Security:** Spring Security JWT (RS-256)  
- **Database:** PostgreSQL  
- **Cache və Locking:** Redis (Redisson RedLock)  
- **Messaging:** RabbitMQ  
- **Docker:** PostgreSQL, Redis, RabbitMQ konteynerləri  
- **Frontend:** HTML, CSS, JS (login, register, text upload və list səhifələri)  

---

## 🌟 Xüsusiyyətlər

- Qeydiyyat və login sistemi (JWT ilə qorunur)  
- Admin panel (istifadəçiləri görmək və deaktiv etmək)  
- Text fayllarının yüklənməsi və siyahıda göstərilməsi  
- Drag-and-drop interfeysi ilə fayl yükləmə  
- Redis caching və RabbitMQ mesajlaşması  
- Redisson RedLock ilə paralel əməliyyatların qarşısının alınması  
- PostgreSQL-də məlumatların saxlanması  

---

## 🐳 Docker Konfiqurasiya

`docker-compose.yml`:

```yaml
services:
  postgres:
    image: postgres:15-alpine
    container_name: postgres
    environment:
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}
    ports:
      - "${POSTGRES_PORT}:5432"
    volumes:
      - data_ferhad:/var/lib/postgresql/data

  redis:
    image: redis:latest
    container_name: redis
    ports:
      - "${REDIS_PORT}:6379"

  rabbitmq:
    image: rabbitmq:3-management-alpine
    container_name: rabbitmq
    ports:
      - "15672:15672"
      - "${RABBITMQ_PORT}:5672"
