package pl.ldworski.patternmatching.balance;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record BankBalance(UUID userId, BigDecimal amount, CurrencyCode currencyCode) {

    public BankBalance {
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currencyCode, "currency must not be null");
    }

    public BankBalance(BigDecimal amount, CurrencyCode currencyCode) {
        this(UUID.randomUUID(), amount, currencyCode);
    }

}
