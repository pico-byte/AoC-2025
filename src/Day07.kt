import java.math.BigInteger

fun main() {

    fun parseGrid(input: List<String>): List<CharArray> {
        val width = input.maxOfOrNull { it.length } ?: 0
        return input.map { it.padEnd(width, ' ').toCharArray() }
    }

    fun part1(input: List<String>): Long {
        var grid = parseGrid(input)
        if (grid.isEmpty()) return 0L
        val rows = grid.size
        val cols = grid[0].size

        var splits = 0L

        for (r in 1 until rows) {
            for (c in 0 until cols) {
                if (grid[r - 1][c] == 'S') {
                    if (grid[r][c] == '^') {
                        if (grid[r - 1][c] == 'S') {
                            grid[r][c + 1] = 'S'
                            grid[r][c - 1] = 'S'
                            splits++
                        }
                    } else {
                        grid[r][c] = 'S'
                        grid[r][c] = 'S'
                    }
                }
            }
        }

        return splits
    }

    fun part2(input: List<String>): BigInteger {
        val grid = parseGrid(input)
        if (grid.isEmpty()) return BigInteger.ZERO
        val rows = grid.size
        val cols = grid[0].size

        var sCol = 70

        var cur = Array(cols) { BigInteger.ZERO }
        cur[sCol] = BigInteger.ONE

        for (r in (1) until rows) {
            val next = Array(cols) { BigInteger.ZERO }
            var any = false
            for (c in 0 until cols) {
                val count = cur[c]
                if (count == BigInteger.ZERO) continue
                if (grid[r][c] == '^') {
                    if (c - 1 >= 0) {
                        next[c - 1] = next[c - 1].add(count)
                        any = true
                    }
                    if (c + 1 < cols) {
                        next[c + 1] = next[c + 1].add(count)
                        any = true
                    }
                } else {
                    next[c] = next[c].add(count)
                    any = true
                }
            }
            cur = next
            if (!any) break
        }
        return cur.fold(BigInteger.ZERO) { acc, v -> acc.add(v) }
    }

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}











//
//fun merge(a: Int, b: Int) {
//    val old = circuits[b]
//    val target = circuits[a]
//    for (k in 0 until points.size)
//        if (circuits[k] == old)
//            circuits[k] = target
//}
//
//var connections = 0
//for ((_, a, b) in pairs) {
//    if (connections == 1000) break
//
//    if (circuits[a] != circuits[b]) {
//        // merge the circuits
//        merge(a, b)
//        connections++
//    }
//}
