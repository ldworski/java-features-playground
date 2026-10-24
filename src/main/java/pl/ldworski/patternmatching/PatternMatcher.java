package pl.ldworski.patternmatching;

import pl.ldworski.patternmatching.balance.BankBalance;
import pl.ldworski.patternmatching.balance.CurrencyCode;
import pl.ldworski.patternmatching.gps.Address;
import pl.ldworski.patternmatching.gps.Car;
import pl.ldworski.patternmatching.gps.Location;
import pl.ldworski.patternmatching.gps.User;
import pl.ldworski.patternmatching.result.Failure;
import pl.ldworski.patternmatching.result.Result;
import pl.ldworski.patternmatching.result.Success;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PatternMatcher {

    public String playWithGuards(Object obj) {
        return switch (obj) {
            case String input when input.length() < 5 -> "Too short";
            case String input when input.length() > 10 -> "Too long";
            case String _ -> "Perfect";
            case null, default -> "Invalid";
        };
    }

    public String playWithSwitch(Object obj) {
        return switch (obj) {
            case String string when string.length() > 1 -> "Text message: " + string;
            case String _ -> "Silence is gold";
            case Number number -> "Your lucky number: " + number;
            case LocalDate _, LocalDateTime _ -> "Always on time";
            case Class<?> clazz -> {
                Constructor<?>[] constructors = clazz.getConstructors();
                yield "Class %s has %s constructor(s)".formatted(clazz.getSimpleName(), constructors.length);
            }
            case null -> "That wasn't nice";
            default -> throw new IllegalStateException("I don't know what to do");
        };
    }

    public String playWithUnnamedPatterns(Object object) {
        if (object instanceof BankBalance(_, BigDecimal amount, CurrencyCode currencyCode)) {
            return "Your account balance is: %s%s".formatted(amount, currencyCode.name());
        }
        return "Unknown balance";
    }

    public String playWithSealedClasses(Result result) {
        return switch (result) {
            case Success(String responseBody) -> "Success: " + responseBody;
            case Failure(Exception exception) ->
                    "Failure: " + (Objects.isNull(exception.getMessage()) ? "no details" : exception.getMessage());
            case null -> "Failure: no details";
        };
    }

    public String playWithNestedPatterns(Object object) {
        return switch (object) {
            case User(UUID uuid, Address(_, Location(double latitude, double longitude))) ->
                    "User with id %s is located here: (%s, %s)".formatted(uuid, latitude, longitude);
            case Car(UUID carId, Location(double latitude, double longitude)) ->
                    "Car with id %s is located here: (%s, %s)".formatted(carId, latitude, longitude);
            case null, default -> "Unknown location";
        };
    }

}
