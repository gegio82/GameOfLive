package it.giorgio.gol.rules;

import it.giorgio.gol.exceptions.InvalidPositionException;
import it.giorgio.gol.model.Board;
import it.giorgio.gol.model.Element;
import it.giorgio.gol.model.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InOrder;

import java.util.Optional;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RulesExecutorTest {

    private final Position POSITION = new Position(1, 1);
    private final Position INVALID_POSITION = new Position(-1, 1);
    private final Board BOARD = mock(Board.class);
    private final Integer LIVE_NEIGHBOURS = 5;
    private final Element ELEMENT = Element.ALIVE;

    @ParameterizedTest
    @EnumSource(Element.class)
    public void whenNoRuleAppliesThenDeath(Element element) {
        when(BOARD.get(POSITION)).thenReturn(Optional.of(element));

        RulesExecutor rulesExecutor = new RulesExecutor();
        Assertions.assertThat(rulesExecutor.applyTo(POSITION, BOARD)).isEqualTo(Element.DEAD);
    }

    @Test
    public void evalRuleForInvalidPosition() {
        when(BOARD.get(INVALID_POSITION)).thenReturn(Optional.empty());

        RulesExecutor rulesExecutor = new RulesExecutor();

        Exception exception = Assertions.catchException(() -> rulesExecutor.applyTo(INVALID_POSITION, BOARD));

        Assertions.assertThat(exception).isInstanceOf(InvalidPositionException.class);
    }

    @Test
    public void ruleSelection() {
        when(BOARD.get(POSITION)).thenReturn(Optional.of(ELEMENT));
        when(BOARD.countLiveNeighbours(POSITION)).thenReturn(LIVE_NEIGHBOURS);

        Rule notMatchesAndNoCondition = mockRule(false,false, Element.DEAD);
        Rule notMatchesButCondition = mockRule(false,true, Element.DEAD);
        Rule matchesButNotCondition = mockRule(true,false, Element.DEAD);
        Rule matchesAndCondition = mockRule(true,true, Element.ALIVE);
        Rule anotherMatchesAndCondition = mockRule(true,true, Element.DEAD);

        RulesExecutor rulesExecutor = new RulesExecutor(
                notMatchesButCondition,
                notMatchesAndNoCondition,
                matchesButNotCondition,
                matchesAndCondition,
                anotherMatchesAndCondition);

        Assertions.assertThat(rulesExecutor.applyTo(POSITION, BOARD)).isEqualTo(Element.ALIVE);

        InOrder inOrder = inOrder(notMatchesAndNoCondition, notMatchesButCondition, matchesButNotCondition, matchesAndCondition, anotherMatchesAndCondition);

        inOrder.verify(notMatchesButCondition).matches(ELEMENT);
        verify(notMatchesButCondition, never()).testConditionOnAliveNeighbours(LIVE_NEIGHBOURS);
        verify(notMatchesButCondition, never()).makeDecision();

        inOrder.verify(notMatchesAndNoCondition).matches(ELEMENT);
        verify(notMatchesAndNoCondition, never()).testConditionOnAliveNeighbours(LIVE_NEIGHBOURS);
        verify(notMatchesAndNoCondition, never()).makeDecision();

        inOrder.verify(matchesButNotCondition).matches(ELEMENT);
        inOrder.verify(matchesButNotCondition).testConditionOnAliveNeighbours(LIVE_NEIGHBOURS);
        verify(matchesButNotCondition, never()).makeDecision();

        inOrder.verify(matchesAndCondition).matches(ELEMENT);
        inOrder.verify(matchesAndCondition).testConditionOnAliveNeighbours(LIVE_NEIGHBOURS);
        verify(matchesAndCondition).makeDecision();

        verify(anotherMatchesAndCondition, never()).matches(ELEMENT);
        verify(anotherMatchesAndCondition, never()).testConditionOnAliveNeighbours(LIVE_NEIGHBOURS);
        verify(anotherMatchesAndCondition, never()).makeDecision();
    }

    private Rule mockRule(boolean matches, boolean testCondition, Element decision) {
        Rule rule = mock(Rule.class);
        when(rule.matches(ELEMENT)).thenReturn(matches);
        when(rule.testConditionOnAliveNeighbours(LIVE_NEIGHBOURS)).thenReturn(testCondition);
        when(rule.makeDecision()).thenReturn(decision);
        return rule;
    }
}