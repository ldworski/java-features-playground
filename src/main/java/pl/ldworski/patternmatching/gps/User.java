package pl.ldworski.patternmatching.gps;

import java.util.Objects;
import java.util.UUID;

public record User(UUID uuid, Address address) {

    public User {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(address, "address must not be null");
    }

    public User(Address address) {
        this(UUID.randomUUID(), address);
    }
}
