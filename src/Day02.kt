//fun main() {
//
//    fun isRepeatedTwice(s: String): Boolean {
//        if (s.length % 2 != 0) return false
//        val half = s.length / 2
//        return s.substring(0, half) == s.substring(half)
//    }
//
//    fun isRepeated(s: String): Boolean {
//        for (divisor in 1..s.length/2){
//            if (s.length % divisor != 0) continue
//            val block = s.substring(0, divisor)
//
//            // Construct repeated block
//            val repeated = block.repeat(s.length / divisor)
//
//            if (repeated == s) return true
//        }
//        return false
//    }
//
//    fun part1(input: List<String>): Long {
//        val line = input.first()
//        val ranges = line.split(",")
//
//        var sum = 0L
//
//        for (r in ranges) {
//            if (r.isBlank()) continue
//
//            val (start, end) = r.split("-").map { it.toLong() }
//
//            for (x in start..end) {
//                val s = x.toString()
//                if (isRepeatedTwice(s)) {
//                    sum += x
//                }
//            }
//        }
//
//        return sum
//    }
//
//    fun part2(input: List<String>): Long {
//        val line = input.first()
//        val ranges = line.split(",")
//
//        var sum = 0L
//
//        for (r in ranges) {
//            if (r.isBlank()) continue
//
//            val (start, end) = r.split("-").map { it.toLong() }
//
//            for (x in start..end) {
//                val s = x.toString()
//                if (isRepeated(s)) {
//                    sum += x
//                }
//            }
//        }
//
//        return sum
//    }
//
//    val input = readInput("input")
//    part1(input).println()
//    part2(input).println()
//}
