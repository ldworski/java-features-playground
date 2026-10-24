package pl.ldworski.patternmatching


import pl.ldworski.patternmatching.balance.BankBalance
import pl.ldworski.patternmatching.balance.CurrencyCode
import pl.ldworski.patternmatching.gps.Address
import pl.ldworski.patternmatching.gps.Car
import pl.ldworski.patternmatching.gps.Location
import pl.ldworski.patternmatching.gps.User
import pl.ldworski.patternmatching.result.Failure
import pl.ldworski.patternmatching.result.Success
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class PatternMatcherSpec extends Specification {

    static final UUID USER_ID = UUID.fromString('11111111-1111-1111-1111-111111111111')
    static final UUID CAR_ID = UUID.fromString('22222222-2222-2222-2222-222222222222')

    var patternMatcher = new PatternMatcher()

    def 'playWithGuards(#obj) returns "#expected"'() {
        expect:
        patternMatcher.playWithGuards(obj) == expected

        where:
        obj             || expected
        ''              || 'Too short'
        'abcd'          || 'Too short'
        'abcde'         || 'Perfect'
        'abcdefghij'    || 'Perfect'
        'abcdefghijk'   || 'Too long'
        42              || 'Invalid'
        LocalDate.now() || 'Invalid'
        null            || 'Invalid'
    }

    def 'playWithSwitch(#obj) returns "#expected"'() {
        expect:
        patternMatcher.playWithSwitch(obj) == expected

        where:
        obj                 || expected
        'hello'             || 'Text message: hello'
        'ab'                || 'Text message: ab'
        'a'                 || 'Silence is gold'
        ''                  || 'Silence is gold'
        42                  || 'Your lucky number: 42'
        3.14                || 'Your lucky number: 3.14'
        2.5d                || 'Your lucky number: 2.5'
        LocalDate.now()     || 'Always on time'
        LocalDateTime.now() || 'Always on time'
        String.class        || 'Class String has 15 constructor(s)'
        Thread.class        || 'Class Thread has 9 constructor(s)'
        null                || "That wasn't nice"
    }

    def 'playWithSwitch throws exception for unsupported type'() {
        when:
        patternMatcher.playWithSwitch(obj)

        then:
        def e = thrown(IllegalStateException)
        e.message == "I don't know what to do"

        where:
        obj << [['a', 'b'], new Object()]
    }

    def 'playWithUnnamedPatterns(#object) returns "#expected"'() {
        expect:
        patternMatcher.playWithUnnamedPatterns(object) == expected

        where:
        object                                                                || expected
        new BankBalance(new BigDecimal('100'), CurrencyCode.PLN)              || 'Your account balance is: 100PLN'
        new BankBalance(new BigDecimal('-0.01'), CurrencyCode.USD)            || 'Your account balance is: -0.01USD'
        new BankBalance(UUID.randomUUID(), BigDecimal.ZERO, CurrencyCode.EUR) || 'Your account balance is: 0EUR'
        '100PLN'                                                              || 'Unknown balance'
        null                                                                  || 'Unknown balance'
    }

    def 'playWithSealedClasses(#result) returns "#expected"'() {
        expect:
        patternMatcher.playWithSealedClasses(result) == expected

        where:
        result                                                   || expected
        new Success('{"status":"ok"}')                           || 'Success: {"status":"ok"}'
        new Success(null)                                        || 'Success: null'
        new Failure(new IllegalArgumentException('bad request')) || 'Failure: bad request'
        new Failure(new RuntimeException())                      || 'Failure: no details'
        null                                                     || 'Failure: no details'
    }

    def 'playWithNestedPatterns(#object) returns "#expected"'() {
        expect:
        patternMatcher.playWithNestedPatterns(object) == expected

        where:
        object                                                                                      || expected
        new User(USER_ID, new Address('Warsaw, Marszalkowska 1', new Location(52.2297d, 21.0122d))) || "User with id $USER_ID is located here: (52.2297, 21.0122)"
        new User(USER_ID, new Address('Null Island', new Location(0d, 0d)))                         || "User with id $USER_ID is located here: (0.0, 0.0)"
        new Car(CAR_ID, new Location(-33.8688d, 151.2093d))                                         || "Car with id $CAR_ID is located here: (-33.8688, 151.2093)"
        new Location(52.2297d, 21.0122d)                                                            || 'Unknown location'
        new Address('Warsaw', new Location(52.2297d, 21.0122d))                                     || 'Unknown location'
        'somewhere'                                                                                 || 'Unknown location'
        null                                                                                        || 'Unknown location'
    }

}
