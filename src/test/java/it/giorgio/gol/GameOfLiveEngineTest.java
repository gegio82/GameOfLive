package it.giorgio.gol;

import it.giorgio.gol.model.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class GameOfLiveEngineTest {

    @Test
    public void testEngine() {
        Board board = Board.from(List.of("   ", "XXX", "   "));

        GameOfLiveEngine engine = GameOfLiveEngine.getEngine();

        Board nextBoard = engine.generateNext(board);
        String output = print(nextBoard);

        Assertions.assertThat(output).contains(String.join("\n",
                " X ", " X ", " X "
        ));
    }

    private String print(Board nextBoard) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        nextBoard.print(printStream);
        return out.toString();
    }
}