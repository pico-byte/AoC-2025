//fun main() {
//
//    fun part1(input: List<String>): Int {
//        var sum = 0
//
//        for (line in input) {
//            var best = 0
//            for (i in line.indices) {
//                for (j in i+1 until line.length) {
//                    val a = line[i] - '0'
//                    val b = line[j] - '0'
//                    best = maxOf(best, 10*a + b)
//                }
//            }
//            sum += best
//        }
//
//        return sum
//    }
//
//
//
//    fun part2(input: List<String>): Long {
//        var total = 0L
//        val k = 12
//
//        for (line in input) {
//            val n = line.length
//            val remove = n - k
//            var toRemove = remove
//            val stack = ArrayDeque<Char>()
//
//            for (c in line) {
//                while (toRemove > 0 && stack.isNotEmpty() && stack.last() < c) {
//                    stack.removeLast()
//                    toRemove--
//                }
//                stack.addLast(c)
//            }
//
//            val result = stack.take(k).joinToString("").toLong()
//            total += result
//        }
//
//        return total
//    }
//
//    val input = readInput("input")
//    part1(input).println()
//    part2(input).println()
//}
