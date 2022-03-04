package it.giorgio.gol.model;

import it.giorgio.gol.exceptions.InvalidBoardException;
import it.giorgio.gol.exceptions.InvalidPositionException;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import static java.util.function.Predicate.not;

public class Board {
    private final Element[][] elements;

    public Board(int rows, int columns, Function<Position, Element> generationFunction) {
        elements = IntStream.range(0, rows)
                .mapToObj(x -> generateRow(x, columns, generationFunction))
                .toArray(Element[][]::new);
    }

    public static Board from(List<String> data) {
        validateBoardShape(data);
        return new Board(data.size(), data.get(0).length(), position -> Element.fromCode(data.get(position.getX()).charAt(position.getY())));
    }

    private static void validateBoardShape(List<String> data) {
        if (data.isEmpty()) {
            throw new InvalidBoardException("Empty board");
        }
        int columns = data.get(0).length();
        if (!data.stream().allMatch(it -> it.length() == columns)) {
            throw new InvalidBoardException("Board is not rectangular");
        }
    }

    private Element[] generateRow(int row, int columns, Function<Position, Element> generationFunction) {
        return IntStream.range(0, columns)
                .mapToObj(y -> new Position(row, y))
                .map(generationFunction)
                .toArray(Element[]::new);
    }

    public Optional<Element> get(Position position) {
        try {
            return Optional.of(elements[position.getX()][position.getY()]);
        } catch (IndexOutOfBoundsException ignored) {
            return Optional.empty();
        }
    }

    public int countLiveNeighbours(Position position) {
        if (get(position).isEmpty()) {
            throw new InvalidPositionException();
        }
        return (int) IntStream.rangeClosed(-1, 1).boxed().flatMap(x ->
                IntStream.rangeClosed(-1, 1)
                        .mapToObj(y -> new Position(position.getX() + x, position.getY() + y) ))
                .filter(not(position::equals))
                .map(this::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Element::isAlive)
                .count();
    }

    public int rowsCount() {
        return elements.length;
    }

    public int columnsCount() {
        return elements[0].length;
    }

    public void print(PrintStream printStream) {
        IntStream.range(0, elements.length).forEach(x -> {
            IntStream.range(0, elements[x].length)
                    .forEach(y -> printStream.print(elements[x][y].getCode()));
            printStream.println();
        });
    }
}
