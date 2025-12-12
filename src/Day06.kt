fun main() {

    fun parseCephalopodBlocks(input: List<String>): List<Pair<List<Long>, Char>> {
        val width = input.maxOfOrNull { it.length } ?: 0
        val lines = input.map { it.padEnd(width, ' ') }

        val isSep = BooleanArray(width) { c ->
            lines.all { it[c] == ' ' }
        }

        val blocks = mutableListOf<IntRange>()
        var c = 0
        while (c < width) {
            if (!isSep[c]) {
                val start = c
                while (c < width && !isSep[c]) c++
                blocks += (start until c)
            } else c++
        }

        val result = mutableListOf<Pair<List<Long>, Char>>()

        for (block in blocks) {
            var op = '?'
            outer@ for (r in lines.indices.reversed()) {
                for (cc in block) {
                    val ch = lines[r][cc]
                    if (ch == '+' || ch == '*') {
                        op = ch
                        break@outer
                    }
                }
            }
            if (op == '?') error("Operator not found in block $block")

            val nums = mutableListOf<Long>()
            for (col in block.last downTo block.first) {
                val sb = StringBuilder()
                for (row in lines.indices) {
                    val ch = lines[row][col]
                    if (ch in '0'..'9') sb.append(ch)
                }
                if (sb.isNotEmpty()) nums += sb.toString().toLong()
            }

            result += nums to op
        }

        return result
    }

    fun part1(input: List<String>): Long {
        return 0L
    }

    fun part2(input: List<String>): Long {
        val problems = parseCephalopodBlocks(input)
        return problems.sumOf { (nums, op) ->
            when (op) {
                '+' -> nums.sum()
                '*' -> nums.fold(1L) { acc, v -> acc * v }
                else -> error("Invalid operator")
            }
        }
    }

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
