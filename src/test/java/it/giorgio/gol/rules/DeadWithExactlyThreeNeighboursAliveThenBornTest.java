package it.giorgio.gol.rules;


import it.giorgio.gol.model.Element;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class DeadWithExactlyThreeNeighboursAliveThenBornTest {
    private final DeadWithExactlyThreeNeighboursAliveThenBorn rule = new DeadWithExactlyThreeNeighboursAliveThenBorn();

    @ParameterizedTest
    @MethodSource("paramsForRuleMatch")
    public void ruleMatch(Element element, boolean expected) {
        Assertions.assertThat(rule.matches(element)).isEqualTo(expected);
    }

    public static Stream<Arguments> paramsForRuleMatch() {
        return Stream.of(
                Arguments.of(Element.ALIVE, false),
                Arguments.of(Element.DEAD, true)
        );
    }

    @ParameterizedTest
    @MethodSource("paramsForTestCondition")
    public void testCondition(int numberOfNeighboursAlive, boolean expected) {
        Assertions.assertThat(rule.testConditionOnAliveNeighbours(numberOfNeighboursAlive)).isEqualTo(expected);
    }

    public static Stream<Arguments> paramsForTestCondition() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, false),
                Arguments.of(2, false),
                Arguments.of(3, true),
                Arguments.of(4, false),
                Arguments.of(5, false),
                Arguments.of(6, false),
                Arguments.of(7, false),
                Arguments.of(8, false)
        );
    }

    @Test
    public void makeDecision() {
        Assertions.assertThat(rule.makeDecision()).isEqualTo(Element.ALIVE);
    }
}