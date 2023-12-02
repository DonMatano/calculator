package com.matano.calculator

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(TokenizationTest::class, InfixToPostfixTokenConverter::class)
class CalculatorUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, Calculator.add(2,2))
    }
    @Test
    fun subtraction_isCorrect() {
        assertEquals(0, Calculator.subtract(2,2))
    }
    @Test
    fun divide_isCorrect() {
        assertEquals(1, Calculator.divide(2,2))
    }
    @Test
    fun multiply_isCorrect() {
        assertEquals(4, Calculator.multiply(2,2))
    }
    @Test
    fun divide_throws_error_on_zero() {
        val exception = assertThrows(ArithmeticException::class.java) {
            Calculator.divide(3,0)
        }
        assertEquals("/ by zero", exception.message)
    }
}
class TokenizationTest {
    @Test
    fun returns_right_add_operation_list_from_given_string() {
        val value = "5+5"
        val expect = listOf("5",Token.ADD.symbol,"5")
        val gotten = Calculator.tokenizeString(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun returns_right_subtraction_operation_list_from_given_string() {
        val value = "5-5"
        val expect = listOf("5",Token.SUBTRACT.symbol, "5")
        val gotten = Calculator.tokenizeString(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun tokenizes_55_x_5_correctly() {
        val value = "55x5"
        val expect = listOf("55", Token.MULTIPLY.symbol, "5")
        val gotten = Calculator.tokenizeString(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun tokenizes_55_plus_5_minus_7_correctly() {
        val value = "55+5-7"
        val expect = listOf("55", Token.ADD.symbol, "5", Token.SUBTRACT.symbol, "7")
        val gotten = Calculator.tokenizeString(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun tokenizes_expression_with_brackets_correctly() {
        val value = "55+(5-7)"
        val expect = listOf("55", Token.ADD.symbol, Token.OPEN_BRACKET.symbol, "5", Token.SUBTRACT.symbol, "7", Token.CLOSE_BRACKET.symbol)
        val gotten = Calculator.tokenizeString(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun tokenizes_expression_with_float_correctly() {
        val value = "55+0.55"
        val expect = listOf("55", Token.ADD.symbol, "0.55")
        val gotten = Calculator.tokenizeString(value)
        assertEquals(expect, gotten)
    }

    class TokenizationError {
        @Test
        fun tokenize_returns_error_if_there_is_an_invalid_character() {

            val exception = assertThrows(CalculatorTokenizationException::class.java) {
                Calculator.tokenizeString("4+4wrong")
            }
            assertEquals(Calculator.TokenizationExceptionMessage, exception.message)
        }

    }

}

class InfixToPostfixTokenConverter {
    @Test
    fun convert_1_plus_1_to_postfix() {
        val value = listOf<String>("1",Token.ADD.symbol, "1")
        val expect = listOf<String>("1","1",Token.ADD.symbol)
        val gotten = Calculator.convertInfixToPostfix(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun convert_2_minus_1_to_postfix() {
        val value = listOf<String>("2",Token.SUBTRACT.symbol, "1")
        val expect = listOf<String>("2","1",Token.SUBTRACT.symbol)
        val gotten = Calculator.convertInfixToPostfix(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun convert_2_minus_1_plus_7_to_postfix() {
        val value = listOf<String>("2",Token.SUBTRACT.symbol, "1", Token.ADD.symbol, "7")
        val expect = listOf<String>("2","1", "7",Token.SUBTRACT.symbol, Token.ADD.symbol)
        val gotten = Calculator.convertInfixToPostfix(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun convert_2_plus_1_multiply_5_to_postfix() {
        val value = listOf<String>("2",Token.ADD.symbol, "1", Token.MULTIPLY.symbol, "5")
        val expect = listOf<String>("2","1","5",Token.MULTIPLY.symbol, Token.ADD.symbol)
        val gotten = Calculator.convertInfixToPostfix(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun convert_2_plus_1_multiply_5_subtract_1_to_postfix() {
        val value = listOf<String>("2",Token.ADD.symbol, "1", Token.MULTIPLY.symbol, "5", Token.SUBTRACT.symbol, "1")
        val expect = listOf<String>("2","1","5",Token.MULTIPLY.symbol, Token.ADD.symbol,"1", Token.SUBTRACT.symbol)
        val gotten = Calculator.convertInfixToPostfix(value)
        assertEquals(expect, gotten)
    }
    @Test
    fun convert_expression_with_brackets_to_postfix() {
        // 2+1*(1+1)
        val value = listOf<String>("2",Token.ADD.symbol, "1", Token.MULTIPLY.symbol, Token.OPEN_BRACKET.symbol, "1", Token.ADD.symbol, "1", Token.CLOSE_BRACKET.symbol  )
        // 2111+*+
        val expect = listOf<String>("2","1","1","1",Token.ADD.symbol, Token.MULTIPLY.symbol, Token.ADD.symbol)
        val gotten = Calculator.convertInfixToPostfix(value)
        assertEquals(expect, gotten)
    }
}
