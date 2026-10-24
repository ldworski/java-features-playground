package pl.ldworski.patternmatching.gps;

import java.util.Objects;
import java.util.UUID;

public record Car(UUID carId, Location location) {

    public Car {
        Objects.requireNonNull(carId,  "carId must not be null");
        Objects.requireNonNull(location,  "location must not be null");
    }

    public Car(Location location) {
        this(UUID.randomUUID(), location);
    }
}
