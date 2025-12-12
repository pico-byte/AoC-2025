//fun main() {
//
//    fun part1(input: List<String>): Int {
//        val h = input.size
//        val w = input[0].length
//
//        // mutable grid we actually modify
//        val grid = input.map { it.toCharArray() }.toTypedArray()
//
//        val positions = listOf(
//            -1 to -1, -1 to 0, -1 to 1,
//            0 to -1,          0 to 1,
//            1 to -1,  1 to 0, 1 to 1
//        )
//
//        var removed = 0
//
//        while (true) {
//            val toRemove = mutableListOf<Pair<Int, Int>>()
//
//            for (x in 0 until h) {
//                for (y in 0 until w) {
//                    if (grid[x][y] != '@') continue
//
//                    var neigh = 0
//                    for ((dx, dy) in positions) {
//                        val rr = x + dx
//                        val cc = y + dy
//                        if (rr in 0 until h && cc in 0 until w && grid[rr][cc] == '@') neigh++
//                    }
//
//                    if (neigh < 4) toRemove += x to y
//                }
//            }
//
//            if (toRemove.isEmpty()) break
//
//            for ((x, y) in toRemove) grid[x][y] = '.'
//            removed += toRemove.size
//        }
//
//        return removed
//    }
//
//
//    fun part2(input: List<String>): Long {
//        return 0
//    }
//
//    val input = readInput("input")
//    part1(input).println()
//    part2(input).println()
//}
