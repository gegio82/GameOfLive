package it.giorgio.gol.rules;

import it.giorgio.gol.exceptions.InvalidPositionException;
import it.giorgio.gol.model.Board;
import it.giorgio.gol.model.Element;
import it.giorgio.gol.model.Position;
import lombok.NonNull;

import java.util.Arrays;

public class RulesExecutor {

    private final Rule[] rules;

    public RulesExecutor(Rule... rules){
        this.rules = rules;
    }

    public Element applyTo(@NonNull Position position, @NonNull Board board) {
        Element element = board.get(position).orElseThrow(InvalidPositionException::new);
        int numbersOfNeighboursAlive = board.countLiveNeighbours(position);
        return Arrays.stream(rules)
                .filter(rule -> rule.matches(element))
                .filter(rule -> rule.testConditionOnAliveNeighbours(numbersOfNeighboursAlive))
                .map(Rule::makeDecision)
                .findFirst()
                .orElse(Element.DEAD);
    }
}
