# microservices

Repositorio para la formación en microservicios para el equipo ECI.

## Shows domain

![Shows domain](domain.svg)

## Helpful things

### Create fresh MongoDB container

```bash
docker run -d --name shows-database -p 27017:27017 mongo
```

### Sample data for MongoDB

*Database*: shows
*Collection*: shows

Sample data:

```json
[{
  "name": "Passengers",
  "availablePlatforms": ["Netflix"],
  "reviews": [{
    "rating": 8,
    "comment": "Ciencia ficción pura de la buena. Muy recomendable."
  }]
},{
  "name": "Tron",
  "availablePlatforms": ["Disney+"]
},{
  "name": "Tenet",
  "availablePlatforms" : ["Filmin", "HBO"]
}]
```

### Configure Kafka with Docker

```bash
docker network create shows
```

Create alias:

```bash
alias startShowKafka='docker-compose -f /path/to/infraestructure.yml up -d'
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