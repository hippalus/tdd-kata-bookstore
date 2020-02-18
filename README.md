# tdd-kata-bookstore

[![Build Status](https://travis-ci.com/hippalus/tdd-kata-bookstore.svg?token=MQxnYM2m6c4bGCSZHAej&branch=master)](https://travis-ci.com/hippalus/tdd-kata-bookstore)
[![codecov](https://codecov.io/gh/hippalus/tdd-kata-bookstore/branch/master/graph/badge.svg?token=D15ffXBsNV)](https://codecov.io/gh/hippalus/tdd-kata-bookstore)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.bookstore%3Atdd-kata-bookstore&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.bookstore%3Atdd-kata-bookstore)


# Rules:
 
Book has a name, category and price.+

Category has name.+

Category has not a parent category.+

Book belongs to a category.+

Book can exists in multiple bookstore.+

Book price changes according to bookstore city.+

# Tasks:

Implement a Restful Apis to create a category, book, bookstore+

Implement a Restful Apis to list all categories, books, bookstores+

Implement a Restful Apis to list books according to category and/or bookstore + 

Implement a Restful Api to add a book to the bookstore+

Implement a Restful Api to remove book from bookstore+

Implement a Restful Api to change book's category+

# Build & Create Image

mvn clean package

mvn dockerfile:build

# Run Container

docker run -p8080:8080  hippalus/bookstore-api

# TODO LIST

Implement worst case test and Exception handling

Refactor test classes

Refactor general project

Api documentation

Postman Collections

From in memory database to Postgres




 