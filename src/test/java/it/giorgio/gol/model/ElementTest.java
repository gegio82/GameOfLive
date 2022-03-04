package it.giorgio.gol.model;

import it.giorgio.gol.exceptions.InvalidElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ElementTest {

    @ParameterizedTest
    @EnumSource(Element.class)
    public void parse(Element element) {
        assertThat(Element.fromCode(element.getCode())).isEqualTo(element);
    }

    @Test
    public void parseInvalidChar() {
        assertThatExceptionOfType(InvalidElementException.class)
                        .isThrownBy(() -> Element.fromCode('G'));
    }
}