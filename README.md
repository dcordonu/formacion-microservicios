# microservices

Repositorio para la formación en microservicios para el equipo ECI.

## Diagrama de dominio

![Shows domain](domain.png)

## Entorno de desarrollo

Crear red interna:

```bash
docker network create shows
```

Iniciar Kafka + MongoDB:

```bash
docker-compose -f /path/to/infraestructure.yml up -d
```

Detener entorno:

```bash
docker-compose -f /path/to/infraestructure.yml stop
```

Borrar entorno:

```bash
docker-compose -f /path/to/infraestructure.yml down
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
