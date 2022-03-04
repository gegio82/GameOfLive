package it.giorgio.gol;

import it.giorgio.gol.model.Board;
import it.giorgio.gol.model.Element;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameOfLiveEngine engine = GameOfLiveEngine.getEngine();

        Board board = randomBoard(20, 20);
        board.print(System.out);
        while (true) {
            Thread.sleep(500);
            board = engine.generateNext(board);
            System.out.print("\033[" + board.rowsCount() + "A\r\033[J");
            board.print(System.out);
            System.out.flush();
        }
    }

    public static Board randomBoard(int rows, int columns) {
        Random random = new Random();
        return new Board(rows, columns, ignored -> random.nextBoolean() ? Element.ALIVE : Element.DEAD);
    }

    public static Board blinker() {
        return Board.from(List.of("     ", "  X  ", "  X  ", "  X  ", "     "));
    }
}
