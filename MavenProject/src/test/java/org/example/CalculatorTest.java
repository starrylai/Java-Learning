package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("加法测试：1 + 2 = 3")
    void add() {
        assertEquals(3, calculator.add(1, 2));
    }

    @Test
    void divide_正常情况() {
        assertEquals(2, calculator.divide(4, 2));
    }

    @Test
    void divide_除零异常() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(4, 0)
        );
        assertEquals("Divisor cannot be zero", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "5, 3, 8",
            "0, 0, 0"
    })
    void add_参数化测试(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }
}