# store-parent

Maven **aggregator** (multi-module) project for Store. The parent POM only wires modules and policy; **all application code lives in child modules** (today: `backoffice`).

## Requirements

- **JDK 21**
- **Maven 3.9+** (or use the included wrapper scripts)

## Build

From the repository root:

```bash
./mvnw clean install
```

Windows (PowerShell or `cmd`):

```bash
mvnw.cmd clean install
```

If you use a system Maven:

```bash
mvn clean install
```

A successful install runs the **backoffice** module’s OpenAPI code generation, compiles Quarkus sources, executes unit tests (in-memory H2; no Docker required), and installs artifacts to your local repository.

## Modules

| Module       | Role |
|-------------|------|
| **backoffice** | Quarkus application: REST API, JPA/Panache, PostgreSQL at runtime. See [backoffice/README.md](backoffice/README.md). |

## Parent POM

The parent is `packaging` **pom** only. An enforcer rule blocks adding `src/main/java` or `src/test/java` at the root so implementation stays in modules.

## Documentation

- [backoffice/README.md](backoffice/README.md) — local development, database, OpenAPI, testing, packaging.

## References

- [Quarkus](https://quarkus.io/)
- [Maven](https://maven.apache.org/)
