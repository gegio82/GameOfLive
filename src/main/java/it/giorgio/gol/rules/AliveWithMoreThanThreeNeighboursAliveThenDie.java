package it.giorgio.gol.rules;

import it.giorgio.gol.model.Element;

public class AliveWithMoreThanThreeNeighboursAliveThenDie implements Rule {

    @Override
    public boolean matches(Element element) {
        return element.isAlive();
    }

    @Override
    public boolean testConditionOnAliveNeighbours(int numberOfNeighboursAlive) {
        return numberOfNeighboursAlive > 3;
    }

    @Override
    public Element makeDecision() {
        return Element.DEAD;
    }
}
