package it.giorgio.gol.rules;

import it.giorgio.gol.model.Element;

public interface Rule {

    boolean matches(Element element);

    boolean testConditionOnAliveNeighbours(int numberOfNeighboursAlive) ;

    Element makeDecision();
}
