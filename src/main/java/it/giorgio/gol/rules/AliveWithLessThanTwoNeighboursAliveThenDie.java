package it.giorgio.gol.rules;

import it.giorgio.gol.model.Element;

public class AliveWithLessThanTwoNeighboursAliveThenDie implements Rule {

    @Override
    public boolean matches(Element element) {
        return element.isAlive();
    }

    @Override
    public boolean testConditionOnAliveNeighbours(int numberOfNeighboursAlive) {
        return numberOfNeighboursAlive < 2;
    }

    @Override
    public Element makeDecision() {
        return Element.DEAD;
    }
}
