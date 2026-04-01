# backoffice

Quarkus **3.32** service (Java **21**) for store back-office style APIs: REST (RESTEasy Classic + Jackson), Hibernate ORM with Panache, and **PostgreSQL** in normal runtime configuration.

## What is implemented today

JAX-RS resources and services exist for:

- **`GET /catalog`** — full catalog (categories and nested products)
- **`/products`** — list, create, get by id, update, delete (see OpenAPI for exact paths and methods)
- **`GET /hello`** — sample endpoint

### Product API errors

Product flows use domain exceptions under `com.storebackoffice.exception` and a Jakarta REST **`@Provider`** **`ExceptionMapper<ProductException>`** (`ProductExceptionMapper`) so clients get **JSON** error bodies with a stable shape:

| Field | Description |
|-------|-------------|
| `status` | HTTP status (e.g. `404`, `400`) |
| `code` | Machine-readable code (e.g. `PRODUCT_NOT_FOUND`, `PRODUCT_BAD_REQUEST`) |
| `message` | Human-readable detail |
| `path` | Request path (from `UriInfo`, typically prefixed with `/`) |
| `timestamp` | ISO-8601 instant |

`ProductService` throws **`ProductNotFoundException`** and **`ProductBadRequestException`** instead of generic JAX-RS exceptions so all **`/products`** errors go through this mapper.

Regression coverage: `src/test/java/.../ProductExceptionHandlerTest.java`.

The machine-readable contract used for **code generation** is `src/main/resources/openapi/sample-openapi.yaml` (OpenAPI **3.0.3**). That file also describes **orders** and **`GET /categories`**; those paths are not backed by application classes yet unless you add them.

A second spec, `backoffice-openapi.yaml`, is kept in the repo for other clients or future alignment; **Maven does not generate from it** unless you change `backoffice/pom.xml`.

## Prerequisites (local run)

- **JDK 21**
- **Maven 3.9+** or `./mvnw` / `mvnw.cmd` from the repo root or this module
- **PostgreSQL** reachable at the JDBC URL you configure (see below)

### Datasource configuration

Secrets are **not** stored in the repository. Set them in your environment (or a local **`.env`** file in the project root or module; that file is gitignored).

| Purpose | Configuration |
|--------|-----------------|
| Password (required for dev against a password-protected server) | `QUARKUS_DATASOURCE_PASSWORD` |
| Username (optional) | `QUARKUS_DATASOURCE_USERNAME` — defaults to `postgres` only if unset |
| JDBC URL (optional) | `QUARKUS_DATASOURCE_JDBC_URL` — defaults to a local `jdbc:postgresql://…` URL if unset |

Hibernate is set to **`update`** in `application.properties` so schema is applied from entities on startup.

### Optional seed data

After tables exist, you can load sample rows:

```bash
psql -U <your-db-user> -d <your-database> -f src/main/resources/db/import-data.sql
```

Paths are relative to the `backoffice` module directory.

## OpenAPI → Java

On **`mvn compile`** / **`mvn install`**, the **openapi-generator-maven-plugin** runs before compilation and emits:

- **Interfaces** under `com.storebackoffice.interfaces` (e.g. `CatalogApi`, `ProductsApi`)
- **Models** under `com.storebackoffice.api.model`

Output directory: `target/generated-sources/openapi/...` (added to compile sources by **build-helper-maven-plugin**).

**Building from the reactor root on Windows:** the POM resolves the spec via a **`file:`** URL produced in the **`initialize`** phase (Ant + **properties-maven-plugin**) so the generator and OpenAPI tooling do not choke on `C:\...` paths.

If generated types are missing in the IDE, run **`mvn generate-sources`** or a full **`mvn install`** once.

## Run (development)

```bash
cd backoffice
../mvnw quarkus:dev
```

Or from repo root:

```bash
./mvnw -pl backoffice quarkus:dev
```

- **HTTP:** [http://localhost:8088](http://localhost:8088) (`quarkus.http.port`)
- **Dev UI:** [http://localhost:8088/q/dev/](http://localhost:8088/q/dev/)

Quick check:

```bash
curl -s -H "Accept: application/json" http://localhost:8088/catalog
```

## Test configuration

- **`%test`** uses **in-memory H2** and **`src/test/resources/import-test-h2.sql`** so **`mvn test`** does **not** require Docker or a running PostgreSQL instance.
- **`quarkus-jdbc-h2`** is declared with **`test`** scope only; the runnable application still uses **PostgreSQL** via `quarkus-jdbc-postgresql`.
- PostgreSQL-oriented seed for optional Docker-based workflows remains in **`import-test.sql`** (uses PostgreSQL `setval`); it is not selected by default test config.

## Build and quality gates

| Goal | Purpose |
|------|---------|
| `mvn test` | Unit / `@QuarkusTest` with H2 (includes catalog and product exception handler tests) |
| `mvn verify -DskipITs=false` | Includes **Failsafe** integration tests (e.g. `CatalogResourceIT` packaged mode); default POM sets **`skipITs=true`** |
| `mvn package` | Produces `target/quarkus-app/` runnable layout |

Run the packaged app:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

## Project layout

```
src/main/java/com/storebackoffice/
├── controller/     # JAX-RS resources implementing generated API interfaces
├── service/        # Business logic
├── repository/     # Panache repositories
├── entity/         # JPA entities
├── exception/      # Product domain exceptions + ExceptionMapper (JSON error payloads)
└── ...

src/main/resources/
├── application.properties
├── openapi/
│   ├── sample-openapi.yaml   # input to OpenAPI Generator (see pom.xml)
│   └── backoffice-openapi.yaml
└── db/
    └── import-data.sql

target/generated-sources/openapi/   # generated interfaces & models (after build)
```

## Learn more

- [Quarkus](https://quarkus.io/)
- [REST with Quarkus](https://quarkus.io/guides/rest) (RESTEasy Classic / REST layer)
- [Jakarta REST `ExceptionMapper`](https://javadoc.io/doc/jakarta.ws.rs/jakarta.ws.rs-api/latest/jakarta.ws.rs/jakarta/ws/rs/ext/ExceptionMapper.html) (used by `ProductExceptionMapper`)
- [OpenAPI Generator](https://openapi-generator.tech/)
- [Hibernate ORM with Quarkus](https://quarkus.io/guides/hibernate-orm)
