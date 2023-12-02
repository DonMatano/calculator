package com.matano.calculator

import java.lang.StringBuilder
import java.util.LinkedList

class CalculatorTokenizationException(message: String) : Exception(message)
abstract class Calculator {

    companion object {
        const val TokenizationExceptionMessage = "The format of string to be tokenized is incorrect"
        fun add(valueOne: Double, valueTwo: Double): Double {
            return valueOne + valueTwo
        }

        fun add(x: Int, y: Int): Int {
            return x + y
        }

        fun subtract(x: Double, y: Double): Double {
            return x - y
        }

        fun subtract(x: Int, y: Int): Int {
            return x - y
        }

        fun multiply(x: Double, y: Double): Double {
            return x * y
        }

        fun multiply(x: Int, y: Int): Int {
            return x * y
        }

        fun divide(x: Double, y: Double): Double {
            return x / y
        }

        fun divide(x: Int, y: Int): Int {
            return x / y
        }

        fun tokenizeString(expression: String): List<String> {
            val tokens = mutableListOf<String>()
            val listOfOperands = listOf<String>(
                Token.MULTIPLY.symbol,
                Token.SUBTRACT.symbol,
                Token.ADD.symbol,
                Token.OPEN_BRACKET.symbol,
                Token.CLOSE_BRACKET.symbol
            )
            var currentNumber = StringBuilder()
            fun addCurrentNumberToTokenList() {
                if (currentNumber.isNotBlank()) {
                    tokens.add(currentNumber.toString())
                    currentNumber.clear()
                }
            }
            for (char in expression) {
                when {
                    char.isDigit() || char.toString() == Token.DECIMAL_POINT.symbol -> currentNumber.append(
                        char
                    )

                    char.toString() in listOfOperands -> {
                        addCurrentNumberToTokenList()
                        tokens.add(char.toString())
                    }

                    char.isWhitespace() -> {
                        addCurrentNumberToTokenList()
                    }

                    else -> throw CalculatorTokenizationException(TokenizationExceptionMessage)
                }
            }
            addCurrentNumberToTokenList()
            return tokens
        }

        private fun mapStringToToken(tokenInString: String): Token {
            return when (tokenInString) {
                Token.ADD.symbol -> Token.ADD
                Token.SUBTRACT.symbol -> Token.SUBTRACT
                Token.DIVIDE.symbol -> Token.DIVIDE
                Token.MULTIPLY.symbol -> Token.MULTIPLY
                Token.OPEN_BRACKET.symbol -> Token.OPEN_BRACKET
                Token.CLOSE_BRACKET.symbol -> Token.CLOSE_BRACKET
                else -> throw Exception("Invalid Token Operator")
            }
        }

        private fun getOperandPrecedence(operandToCheck: Token): Int {
            val listOfOperandsWithPrecedenceTwo = listOf<Token>(Token.MULTIPLY, Token.DIVIDE)
            return if (listOfOperandsWithPrecedenceTwo.contains(operandToCheck)) 2 else 1
        }

        private fun shuntingYardAlgorithm(tokensInInfix: List<String>): List<String> {
            val operandsStack = ArrayDeque<Token>()
            val outputQueue = LinkedList<String>()
            val listOfOperands = listOf<String>(
                Token.ADD.symbol,
                Token.SUBTRACT.symbol,
                Token.OPEN_BRACKET.symbol,
                Token.CLOSE_BRACKET.symbol,
                Token.MULTIPLY.symbol,
                Token.DIVIDE.symbol
            )
            // Go through all tokens.
            // If it's a closing bracket pop the stack operands to the queue until you find the opening bracket.
            // Dispose both brackets.
            // If none or is a bracket just add it to the stack
            // Pop the operand in the stack and store it to the queue
            // Continue popping till you get the a lower precedence operand or the stack is empty.
            // Add the operand to the stack.
            for (token in tokensInInfix) when {
                // If token is a digit add it to the outputQueue
                // token is not an operand it means it's a digit
                token !in listOfOperands -> {
                    outputQueue.addLast(token)
                }
                // If it's not a digit it can be an operand
                // Check in operatorsStack if it has an operand or
                // if it's an opening bracket add it to the stack
                operandsStack.isEmpty() || token == Token.OPEN_BRACKET.symbol -> {
                    // Doesn't have an operand so just add the operand
                    operandsStack.addFirst(mapStringToToken(token))
                }
                // If it's a closing bracket. Pop the stack and add it to the output till you find the opening bracket
                token == Token.CLOSE_BRACKET.symbol -> {
                    while (operandsStack.firstOrNull() != Token.OPEN_BRACKET) {
                        if (operandsStack.isEmpty()) {
                            break
                        }
                        outputQueue.addLast(operandsStack.removeFirst().symbol)
                    }
                    // We have found the open bracket. Pop it off the stack and dispose it
                    operandsStack.removeFirst()
                }
                // If there is an operand check the precedence of the operand
                else -> {
                    // If has a lower precedence or it's same  than the found operand
                    while (!operandsStack.isEmpty()
                        && operandsStack.first() != Token.OPEN_BRACKET
                        && getOperandPrecedence(
                            mapStringToToken(token)
                        ) <= getOperandPrecedence(
                            operandsStack.first()
                        )
                    ) {
                        // Remove the operand from the stack and add it to the queue
                        outputQueue.addLast(operandsStack.removeFirst().symbol)
                    }
                    operandsStack.addFirst(mapStringToToken(token))
                }
            }
            // If all tokens are read pop the remaining operands in the stack to the queue
            while (!operandsStack.isEmpty()) {
                outputQueue.add(operandsStack.removeFirst().symbol)
            }
            return outputQueue
        }

        fun convertInfixToPostfix(tokensInInfix: List<String>): List<String> {
            return shuntingYardAlgorithm(tokensInInfix)
        }
    }
}