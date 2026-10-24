package pl.ldworski.patternmatching.result;

import java.util.Objects;

public record Failure(Exception exception) implements Result {

    public Failure {
        Objects.requireNonNull(exception, "exception must be not null");
    }
}
