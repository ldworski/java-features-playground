import spock.lang.Specification

class CalculatorSpec extends Specification {

    def calculator = new Calculator()

    def "adds #a and #b to get #result"() {
        expect:
        calculator.add(a, b) == result

        where:
        a  | b  || result
        1  | 2  || 3
        -1 | 1  || 0
        0  | 0  || 0
    }

    def "throws on division by zero"() {
        when:
        calculator.divide(1, 0)

        then:
        thrown(ArithmeticException)
    }
}
