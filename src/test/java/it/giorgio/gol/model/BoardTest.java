package it.giorgio.gol.model;

import it.giorgio.gol.exceptions.InvalidBoardException;
import it.giorgio.gol.exceptions.InvalidElementException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class BoardTest {

    @Test
    public void parseAndGet() {
        Board board = Board.from(List.of(" X", " X", "XX"));

        Assertions.assertThat(board.rowsCount()).isEqualTo(3);
        Assertions.assertThat(board.columnsCount()).isEqualTo(2);
        Assertions.assertThat(board.get(new Position(0,0))).contains(Element.DEAD);
        Assertions.assertThat(board.get(new Position(0,1))).contains(Element.ALIVE);
        Assertions.assertThat(board.get(new Position(1,0))).contains(Element.DEAD);
        Assertions.assertThat(board.get(new Position(1,1))).contains(Element.ALIVE);
        Assertions.assertThat(board.get(new Position(2,0))).contains(Element.ALIVE);
        Assertions.assertThat(board.get(new Position(2,1))).contains(Element.ALIVE);
    }

    @Test
    public void parse_emptyList() {
        Assertions.assertThatExceptionOfType(InvalidBoardException.class)
                .isThrownBy(() -> Board.from(Collections.emptyList()))
                .withMessage("Empty board");
    }

    @Test
    public void parse_notRectangular() {
        Assertions.assertThatExceptionOfType(InvalidBoardException.class)
                .isThrownBy(() -> Board.from(List.of("XXX", "XX")))
                .withMessage("Board is not rectangular");
    }

    @Test
    public void parse_invalidElement() {
        Assertions.assertThatExceptionOfType(InvalidElementException.class)
                .isThrownBy(() -> Board.from(List.of("XXX", "XXK")));
    }

    @ParameterizedTest
    @MethodSource("parametersForGetOutOfBorder")
    public void getOutOfBorder(Position position) {
        Board board = Board.from(List.of(" X ", " X ", "XXX"));

        Assertions.assertThat(board.get(position)).isEmpty();
    }

    public static Stream<Arguments> parametersForGetOutOfBorder() {
        return Stream.of(
                Arguments.of(new Position(-1,1)),
                Arguments.of(new Position(1,-1)),
                Arguments.of(new Position(3,1)),
                Arguments.of(new Position(1,3))
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForCountLiveNeighbours")
    public void countLiveNeighbours(Board board, int expected) {
        Assertions.assertThat(board.countLiveNeighbours(new Position(1, 1))).isEqualTo(expected);
    }

    public static Stream<Arguments> parametersForCountLiveNeighbours() {
        return Stream.of(
                Arguments.of(Board.from(List.of("   ", "   ", "   ")), 0),
                Arguments.of(Board.from(List.of("X  ", "   ", "   ")), 1),
                Arguments.of(Board.from(List.of("XX ", "   ", "   ")), 2),
                Arguments.of(Board.from(List.of("XXX", "   ", "   ")), 3),
                Arguments.of(Board.from(List.of("XXX", "X  ", "   ")), 4),
                Arguments.of(Board.from(List.of("XXX", "X X", "   ")), 5),
                Arguments.of(Board.from(List.of("XXX", "X X", "X  ")), 6),
                Arguments.of(Board.from(List.of("XXX", "X X", "XX ")), 7),
                Arguments.of(Board.from(List.of("XXX", "X X", "XXX")), 8),
                Arguments.of(Board.from(List.of("   ", " X ", "   ")), 0),
                Arguments.of(Board.from(List.of("X  ", " X ", "   ")), 1),
                Arguments.of(Board.from(List.of("XX ", " X ", "   ")), 2),
                Arguments.of(Board.from(List.of("XXX", " X ", "   ")), 3),
                Arguments.of(Board.from(List.of("XXX", "XX ", "   ")), 4),
                Arguments.of(Board.from(List.of("XXX", "XXX", "   ")), 5),
                Arguments.of(Board.from(List.of("XXX", "XXX", "X  ")), 6),
                Arguments.of(Board.from(List.of("XXX", "XXX", "XX ")), 7),
                Arguments.of(Board.from(List.of("XXX", "XXX", "XXX")), 8)
        );
    }
}