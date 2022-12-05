# JokesDB

A minimal application to play with JPA and spring data topics.

## 🐳 Postgres with Docker

A simple solution expects a https://www.baeldung.com/linux/docker-run-without-sudo[running docker without sudo].
To get a Database connection (and associated JPA-autocomplete), run `./gradlew bootRun` (it will hang).

Alternatively launch a postgres docker container similar to the `dockerPostgres`-Task in `build.gradle` by hand.

## 🪣 IntelliJ Database View

View | Tool Windows | Database | + | Data Source from URL
```
jdbc:postgresql://localhost:5432/localdb
User: localuser, Password: localpass
```

# Contribution guide

## Style guide

Use the default Java Spring naming conventions. Services `*Service`, repositories `*Repository`, controllers `*Controller`.

Formatting uses the default IntelliJ config, whatever that is.

## Pull requests

Changes must be submitted via a pull request. This project uses [bors-ng](https://bors.tech/) to ensure that master is always green.

Every pull request should follow the pull request template and, in spirit with the project, include one joke at the end.