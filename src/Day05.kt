//fun main() {
//
//    fun parseRanges(input: List<String>): List<Pair<Long, Long>> {
//        val blank = input.indexOfFirst { it.isBlank() }
//        val sep = if (blank != -1) blank else input.size
//
//        return input.subList(0, sep)
//            .filter { it.isNotBlank() }
//            .map {
//                val (l, r) = it.split("-").map(String::toLong)
//                l to r
//            }
//    }
//
//    // merge overlapping ranges
//    fun merge(ranges: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
//        if (ranges.isEmpty()) return emptyList()
//
//        val sorted = ranges.sortedBy { it.first }
//        val merged = ArrayList<Pair<Long, Long>>()
//
//        var (curStart, curEnd) = sorted[0]
//
//        for (i in 1 until sorted.size) {
//            val (s, e) = sorted[i]
//
//            if (s <= curEnd + 1) {
//                // overlap or directly touching
//                curEnd = maxOf(curEnd, e)
//            } else {
//                merged += curStart to curEnd
//                curStart = s
//                curEnd = e
//            }
//        }
//        merged += curStart to curEnd
//        return merged
//    }
//
//    fun part1(input: List<String>): Int {
//        // already solved earlier
//        val blank = input.indexOfFirst { it.isBlank() }
//        val ranges = parseRanges(input)
//        val ids = input.drop(blank + 1).filter { it.isNotBlank() }.map(String::toLong)
//        return ids.count { id -> ranges.any { (l, r) -> id in l..r } }
//    }
//
//    fun part2(input: List<String>): Long {
//        val ranges = parseRanges(input)
//        val merged = merge(ranges)
//
//        // count lengths
//        return merged.sumOf { (l, r) -> (r - l + 1) }
//    }
//
//    val input = readInput("input")
//    part1(input).println()
//    part2(input).println()
//}
