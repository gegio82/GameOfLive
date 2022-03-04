package it.giorgio.gol;

import it.giorgio.gol.model.Board;
import it.giorgio.gol.rules.AliveWithLessThanTwoNeighboursAliveThenDie;
import it.giorgio.gol.rules.AliveWithMoreThanThreeNeighboursAliveThenDie;
import it.giorgio.gol.rules.AliveWithTwoOrThreeNeighboursAliveThenSurvive;
import it.giorgio.gol.rules.DeadWithExactlyThreeNeighboursAliveThenBorn;
import it.giorgio.gol.rules.RulesExecutor;

public class GameOfLiveEngine {
    private final RulesExecutor rulesExecutor;

    public GameOfLiveEngine(RulesExecutor rulesExecutor) {
        this.rulesExecutor = rulesExecutor;
    }

    public Board generateNext(Board currentBoard) {
        return new Board(
                currentBoard.rowsCount(),
                currentBoard.columnsCount(),
                position -> rulesExecutor.applyTo(position, currentBoard));
    }

    public static GameOfLiveEngine getEngine() {
        RulesExecutor rulesExecutor = new RulesExecutor(
                new AliveWithLessThanTwoNeighboursAliveThenDie(),
                new AliveWithTwoOrThreeNeighboursAliveThenSurvive(),
                new AliveWithMoreThanThreeNeighboursAliveThenDie(),
                new DeadWithExactlyThreeNeighboursAliveThenBorn());

        return new GameOfLiveEngine(rulesExecutor);
    }
}
