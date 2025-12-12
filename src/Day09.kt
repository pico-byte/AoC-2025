import kotlin.math.abs
import kotlin.math.*


fun main() {
    fun parsePoints(input: List<String>): List<LongArray> {
        return input
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { line ->
                val p = line.split(",").map { it.toLong() }
                longArrayOf(p[0], p[1])
            }
    }

    fun part1(input: List<String>): Long {
        val reds = parsePoints(input)
        val size = reds.size
        var largestArea = 0L

        for (i in 0 until size) {
            for (j in i + 1 until size) {

                val width = abs(reds[i][0] - reds[j][0]) + 1
                val height = abs(reds[i][1] - reds[j][1]) + 1
                val area = width * height
                if (area > largestArea) {
                    largestArea = area
                }
            }
        }
        return largestArea
    }

    fun part2(input: List<String>): Long {
        val best = 0L
        return best
    }





    // Read the input from the `src/input.txt` file.
    val input = readInput("input")
    part1(input).println()
    part2(input).println()
    println()
}
