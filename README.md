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
use shows
db.shows.insertMany([{"name": "Passengers", "availablePlatforms": ["Netflix"], "ratings": [{"punctuation": 8, "comment": "Ciencia ficción pura de la buena. Muy recomendable."}]}, {"name": "Tron", "availablePlatforms": ["Disney+"]}, {"name": "Tenet", "availablePlatforms" : ["Filmin", "HBO"]}])
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