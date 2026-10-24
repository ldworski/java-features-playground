package pl.ldworski.patternmatching.gps;

import java.util.Objects;

public record Address(String addressDescription, Location location) {

    public Address {
        Objects.requireNonNull(addressDescription, "addressDescription must not be null");
        Objects.requireNonNull(location, "location must not be null");
    }
}
