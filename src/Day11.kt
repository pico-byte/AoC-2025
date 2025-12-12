fun main() {
    fun part1(input: List<String>): Long {
        val graph = mutableMapOf<String, MutableList<String>>()

        for (line in input) {
            if (line.isBlank()) continue
            val (left, right) = line.split(":")
            val from = left.trim()
            val to = right.trim().split(" ").filter { it.isNotBlank() }
            graph.computeIfAbsent(from) { mutableListOf() }.addAll(to)
        }

        var count = 0L

        fun dfs(node: String, seenDac: Boolean, seenFft: Boolean, visited: MutableSet<String>) {
            val newSeenDac = seenDac || node == "dac"
            val newSeenFft = seenFft || node == "fft"

            if (node == "out") {
                if (newSeenDac && newSeenFft) count++
                return
            }

            if (!visited.add(node)) return

            for (n in graph[node] ?: emptyList()) {
                dfs(n, newSeenDac, newSeenFft, visited.toMutableSet())
            }
        }

        dfs("svr", false, false, mutableSetOf())
        return count
    }


    fun part2(input: List<String>): Long {
        val graph = mutableMapOf<String, List<String>>()
        for (line in input) {
            if (line.isBlank()) continue
            val (left, right) = line.split(":")
            val from = left.trim()
            val to = right.trim().split(" ").filter { it.isNotBlank() }
            graph[from] = to
        }

        val memo = mutableMapOf<Triple<String, Boolean, Boolean>, Long>()

        fun dfs(node: String, seenDac: Boolean, seenFft: Boolean): Long {
            val sD = seenDac || node == "dac"
            val sF = seenFft || node == "fft"

            if (node == "out") {
                return if (sD && sF) 1L else 0L
            }

            val key = Triple(node, sD, sF)
            val maybenull = memo[key]
            if (maybenull != null) return maybenull

            val next = graph[node] ?: emptyList()

            var total = 0L
            for (n in next) {
                total += dfs(n, sD, sF)
            }

            memo[key] = total
            return total
        }

        return dfs("svr", false, false)
    }


    val input = readInput("input")
    //part1(input).println()
    part2(input).println()
}
