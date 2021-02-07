# microservices

Repositorio para la formación en microservicios para el equipo ECI.

## Shows domain

![Shows domain](domain.svg)

## Helpful things

### Create fresh MongoDB container

```bash
docker run -d --name shows-database -p 27017:27017 mongo
```

### Insert Data into MongoDB collection

```
db.shows.insertMany([{"name": "Space Jam", "availablePlatforms": ["HBO", "Flixole"]}, {"name": "Tron", "availablePlatforms": ["Disney+"]}, {"name": "Tenet", "availablePlatforms" : ["Netflix", "HBO"]}])
```

## Topics covered

* Microservices vs. monoliths.
* Spring Boot:
  * Dependency injection through constructors.
  * Model-View-Controller design pattern.
  * Spring Data with MongoDB.
  * Swagger console customization and usage.

Next:

* Lombok + MapStruct
* Unit testing with JUnit, Spring and Mockito.
* Integration tests.