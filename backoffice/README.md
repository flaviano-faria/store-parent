# backoffice

Store backoffice API built with Quarkus. Exposes REST endpoints for catalog, products, orders, and categories. Uses OpenAPI code generation, Hibernate ORM, and PostgreSQL.

## Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL (e.g. Docker container on port 5432 with database `store`)

## Development setup

### 1. Generate API interfaces

Run `mvn clean install` before starting development. This generates JAX-RS interfaces and models from the OpenAPI spec (`src/main/resources/openapi/`). If you see "cannot find symbol" for `CatalogApi`, `ApiCatalog`, etc., run:

```bash
./mvnw clean install
```

On Windows:

```bash
mvnw.cmd clean install
```

### 2. Database

Ensure PostgreSQL is running with a database named `store` on port 5432. Default config in `application.properties`:

- Host: `localhost`
- Port: `5432`
- Database: `store`
- User: `postgres`
- Password: (configure in `application.properties`)

### Seed initial data

The sample data is in:

- `src/main/resources/db/import-data.sql`

After the app creates the tables, you can load the data with `psql`:

```bash
psql -U postgres -d store -f src/main/resources/db/import-data.sql
```

## Running the application

Dev mode (with live reload):

```bash
./mvnw quarkus:dev
```

The API is available at <http://localhost:8088>. Dev UI: <http://localhost:8088/q/dev/>.

## API endpoints

| Method | Path      | Description          |
|--------|-----------|----------------------|
| GET    | /catalog  | Full product catalog |
| GET    | /hello    | Sample endpoint      |

Quick test for catalog:

```bash
curl -s -H "Accept: application/json" http://localhost:8088/catalog
```

## Project structure

```
src/main/java/com/storebackoffice/
├── controller/     # JAX-RS resources (implements OpenAPI interfaces)
├── service/        # Business logic (e.g. CatalogService)
├── repository/     # Quarkus/Panache data access (e.g. CategoryRepository)
├── entity/         # JPA entities (Category, Product)
└── (generated)    # target/generated-sources/openapi → interfaces, models
```

## Testing

Unit tests (in-process):

```bash
./mvnw test
```

Integration tests (packaged app):

```bash
./mvnw verify -DskipITs=false
```

## Packaging

```bash
./mvnw package
```

Run the JAR:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

## Learn more

- [Quarkus](https://quarkus.io/)
- [Quarkus REST](https://quarkus.io/guides/resteasy-reactive)
