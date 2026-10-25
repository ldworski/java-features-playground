# Java Features Playground

A playground project for exploring and learning various Java language features, with a focus on the newest ones.
Each feature is demonstrated with small, self-contained examples and covered by [Spock](https://spockframework.org/) specifications,
so the tests double as executable documentation.

## Features

### Pattern matching (`pl.ldworski.patternmatching`)

### Virtual threads (`pl.ldworski.virtualthreads`)

### Other
- **`java.lang.IO`** – simplified console I/O (`IO.println`)

## Tech stack

- Java 26 (Gradle toolchain)
- Gradle
- Groovy 5 + Spock 2.5 for tests
- GitHub Actions for CI

## Building and running tests

```bash
./gradlew build
```

or tests only:

```bash
./gradlew test
```

## License

See [LICENSE](LICENSE).
