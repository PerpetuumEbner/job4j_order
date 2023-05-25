# Микросервисный проект - Доставка еды "Голодный волк".

[![Java CI](https://github.com/PerpetuumEbner/job4j_order/actions/workflows/maven.yml/badge.svg)](https://github.com/PerpetuumEbner/job4j_order/actions/workflows/maven.yml)

## Общее описание:

Заказ блюд на дом. Блок заказов.
* [Блок блюда](https://github.com/PerpetuumEbner/job4j_dish)
* [Блок кухня](https://github.com/PerpetuumEbner/job4j_kitchen)
* [Блок уведомления](https://github.com/PerpetuumEbner/job4j_notification)

***

## Реализовано:

* Добавление заказа
* Изменение статуса заказа
* Поиск всех заказов
* Отправка заказа на кухню для приготовления блюд. Если блюдо не может быть приготовлено, то заказ отменяется.
* Отправка уведомления при смене статуса

***

## Технологии:

[![java](https://img.shields.io/badge/java-17-red)](https://www.java.com/)
[![maven](https://img.shields.io/badge/apache--maven-3.8.3-blue)](https://maven.apache.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.6.0-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgresSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![Lombok](https://img.shields.io/badge/Lombok-1.18.26-red)](https://projectlombok.org/)
[![Liquibase](https://img.shields.io/badge/Liquibase-4.17.2-orange)](https://www.liquibase.org/)
[![Apache Kafka](https://img.shields.io/badge/Kafka-3.4.0-%20%23000000)](https://kafka.apache.org/)

***

## Запуск проекта:

* создать базу данных `orders`
* `maven install`
* `java -jar target/order-0.0.1-SNAPSHOT.jar`

***

## Структура проекта:

### Создание заказа.
Создаётся заказ с блюдами, по умолчанию присваивается статус "В обработке". Заказ уходит на кухню для приготовления через брокер сообщений.

![1](img/1.jpg)

### Изменение статуса заказа.
Если кухня приготовила все блюда, то статус заказа меняется на "Собран".

![2](img/2.jpg)

Если кухня не может приготовить какое-либо блюдо, то заказ отменяется.

![3](img/3.jpg)

### Отправка уведомлений
Каждый раз при смене статуса заказа отправляется уведомление через брокер сообщений.