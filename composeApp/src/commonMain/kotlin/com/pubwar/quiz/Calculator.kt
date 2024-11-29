class Calculator {

    fun evaluate(expression: String): Double {
        val tokens = expression.toCharArray()

        val values = mutableListOf<Double>()
        val ops = mutableListOf<Char>()

        var i = 0
        while (i < tokens.size) {
            when {
                // Skip whitespace
                tokens[i].isWhitespace() -> i++

                // If token is a number, push it to values stack
                tokens[i].isDigit() -> {
                    val sb = StringBuilder()
                    while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                        sb.append(tokens[i++])
                    }
                    values.add(sb.toString().toDouble())
                    i--
                }

                // If token is an opening brace, push it to ops stack
                tokens[i] == '(' -> ops.add(tokens[i])

                // If token is a closing brace, solve the entire brace
                tokens[i] == ')' -> {
                    while (ops.last() != '(') {
                        values.add(applyOp(ops.removeAt(ops.size - 1), values.removeAt(values.size - 1), values.removeAt(values.size - 1)))
                    }
                    ops.removeAt(ops.size - 1)
                }

                // If token is an operator, push it to ops stack after popping
                // the top two elements for the operation if necessary
                tokens[i] in listOf('+', '-', '*', '/') -> {
                    while (ops.isNotEmpty() && hasPrecedence(tokens[i], ops.last())) {
                        values.add(applyOp(ops.removeAt(ops.size - 1), values.removeAt(values.size - 1), values.removeAt(values.size - 1)))
                    }
                    ops.add(tokens[i])
                }
            }
            i++
        }

        // Entire expression has been parsed, apply remaining ops to remaining values
        while (ops.isNotEmpty()) {
            values.add(applyOp(ops.removeAt(ops.size - 1), values.removeAt(values.size - 1), values.removeAt(values.size - 1)))
        }

        // Top of values stack contains the result
        return values.last()
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false
        return true
    }

    private fun applyOp(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) throw ArithmeticException("Cannot divide by zero")
                a / b
            }
            else -> 0.0
        }
    }
}
