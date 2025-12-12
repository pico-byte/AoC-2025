fun part1(input: List<String>): Long {
    var totalPresses = 0L

    for (line in input) {
        val targetMatch = Regex("\\[(.*?)\\]").find(line)?.groupValues?.get(1) ?: continue
        val buttonsMatch =
            Regex("\\((.*?)\\)").findAll(line).map { it.groupValues[1].split(",").map { it.trim().toInt() } }
                .toList()

        val numLights = targetMatch.length
        val numButtons = buttonsMatch.size

        val matrix = Array(numLights) { IntArray(numButtons + 1) }

        for (i in 0 until numLights) {
            for (j in 0 until numButtons) {
                if (i in buttonsMatch[j]) {
                    matrix[i][j] = 1
                }
            }
            matrix[i][numButtons] = if (targetMatch[i] == '#') 1 else 0
        }

        //ELIMINATION GAUß
        val pivotCol = IntArray(numLights) { -1 }
        var currentRow = 0
        for (col in 0 until numButtons) {
            var pivot = -1
            for (row in currentRow until numLights) {
                if (matrix[row][col] == 1) {
                    pivot = row
                    break
                }
            }
            if (pivot == -1) continue
            val temp = matrix[currentRow]
            matrix[currentRow] = matrix[pivot]
            matrix[pivot] = temp
            pivotCol[currentRow] = col
            for (row in 0 until numLights) {
                if (row != currentRow && matrix[row][col] == 1) {
                    for (c in 0..numButtons) {
                        matrix[row][c] = matrix[row][c] xor matrix[currentRow][c]
                    }
                }
            }
            currentRow++
        }

        var solvable = true
        for (row in currentRow until numLights) {
            if (matrix[row][numButtons] == 1) {
                solvable = false
                break
            }
        }
        if (!solvable) continue

        val used = BooleanArray(numButtons)
        for (i in 0 until currentRow) {
            if (pivotCol[i] != -1) used[pivotCol[i]] = true
        }
        val freeVars = (0 until numButtons).filter { !used[it] }
        var minPresses = Int.MAX_VALUE
        val freeCount = freeVars.size
        val limit = 1 shl freeCount
        for (mask in 0 until limit) {
            val solution = IntArray(numButtons)
            for (i in freeVars.indices) {
                solution[freeVars[i]] = (mask shr i) and 1
            }
            for (i in currentRow - 1 downTo 0) {
                val col = pivotCol[i]
                var sum = matrix[i][numButtons]
                for (j in col + 1 until numButtons) {
                    sum = sum xor (matrix[i][j] * solution[j])
                }
                solution[col] = sum
            }
            val presses = solution.count { it == 1 }
            if (presses < minPresses) minPresses = presses
        }
        totalPresses += minPresses
    }
    return totalPresses
}

fun part2(input: List<String>): Long {
    var totalPresses = 0L
    for (line in input) {
        val targetMatch = Regex("\\{(.*?)\\}").find(line)?.groupValues?.get(1) ?: continue
        val target = targetMatch.split(",").map { it.trim().toInt() }
        val buttonsMatch = Regex("\\((.*?)\\)").findAll(line).map { it.groupValues[1].split(",").map { it.trim().toInt() } }.toList()
        val numCounters = target.size
        val numButtons = buttonsMatch.size
        val matrix = Array(numCounters) { IntArray(numButtons) }
        for (j in 0 until numButtons) {
            for (i in buttonsMatch[j]) {
                matrix[i][j] = 1
            }
        }
        data class State(val counters: List<Int>, val presses: IntArray, val totalPresses: Int)
        val queue = ArrayDeque<State>()
        val seen = mutableSetOf<List<Int>>()
        queue.add(State(List(numCounters) { 0 }, IntArray(numButtons), 0))
        var minPresses = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            if (state.counters == target) {
                if (state.totalPresses < minPresses) {
                    minPresses = state.totalPresses
                }
                continue
            }
            if (state.totalPresses >= minPresses) continue
            for (j in 0 until numButtons) {
                val newCounters = state.counters.toMutableList()
                for (i in 0 until numCounters) {
                    if (matrix[i][j] == 1) newCounters[i]++
                }
                if (newCounters.zip(target).any { (a, b) -> a > b }) continue
                if (seen.add(newCounters)) {
                    val newPresses = state.presses.copyOf()
                    newPresses[j]++
                    queue.add(State(newCounters, newPresses, state.totalPresses + 1))
                }
            }
        }
        totalPresses += if (minPresses == Int.MAX_VALUE) 0 else minPresses
    }
    return totalPresses
}

fun main() {
    println("Day10 main started!")
    val input = readInput("input")
    println("Input size: ${input.size}")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}
