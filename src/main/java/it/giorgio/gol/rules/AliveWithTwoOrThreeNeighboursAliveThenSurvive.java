package it.giorgio.gol.rules;

import it.giorgio.gol.model.Element;

import java.util.List;

public class AliveWithTwoOrThreeNeighboursAliveThenSurvive implements Rule {

    @Override
    public boolean matches(Element element) {
        return element.isAlive();
    }

    @Override
    public boolean testConditionOnAliveNeighbours(int numberOfNeighboursAlive) {
        return List.of(2, 3).contains(numberOfNeighboursAlive);
    }

    @Override
    public Element makeDecision() {
        return Element.ALIVE;
    }
}
