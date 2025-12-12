fun main() {
    fun parsePoints(input: List<String>): List<LongArray> {
        return input
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { line ->
                val p = line.split(",").map { it.toLong() }
                longArrayOf(p[0], p[1], p[2])
            }
    }

    class DSU(n: Int) {
        private val parent = IntArray(n) { it }
        private val size = IntArray(n) { 1 }
        var components = n

        fun find(a: Int): Int {
            var x = a
            while (parent[x] != x) {
                parent[x] = parent[parent[x]]
                x = parent[x]
            }
            return x
        }

        fun union(a: Int, b: Int): Boolean {
            var ra = find(a)
            var rb = find(b)
            if (ra == rb) return false
            if (size[ra] < size[rb]) {
                val t = ra; ra = rb; rb = t
            }
            parent[rb] = ra
            size[ra] += size[rb]
            components--
            return true
        }
    }

    data class Edge(val d: Long, val a: Int, val b: Int)

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Long {
        val pts = parsePoints(input)
        val n = pts.size

        val edges = ArrayList<Edge>(n * (n - 1) / 2)
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                val dx = pts[i][0] - pts[j][0]
                val dy = pts[i][1] - pts[j][1]
                val dz = pts[i][2] - pts[j][2]
                val dist2 = dx * dx + dy * dy + dz * dz
                edges.add(Edge(dist2, i, j))
            }
        }

        edges.sortWith(compareBy { it.d })

        val dsu = DSU(n)

        for (e in edges) {
            if (dsu.union(e.a, e.b)) {
                if (dsu.components == 1) {
                    val x1 = pts[e.a][0]
                    val x2 = pts[e.b][0]
                    return x1 * x2
                }
            }
        }

        return 0L
    }

    // Read the input from the `src/input.txt` file.
    val input = readInput("input")
    part1(input).println()
    part2(input).println()
    println()
}


//    fun part2(input: List<String>): Long {
//        val reds = parsePoints(input)
//        val size = reds.size
//        var largestArea = 0L
//
//        val xvals = reds.sortedBy { it[0] }
//        val width = xvals[size - 1][0] - xvals[0][0]
//
//        val yvals = reds.sortedBy { it[1] }
//        val height = yvals[size-1][1] - yvals[0][1]
//
//        var grid = Array(height.toInt()) {IntArray(width.toInt())}
//
//        fun nx(x: Long) = x - xvals[0][0] + 1
//        fun ny(y: Int) = y - yvals[0][1] + 1
//
//
//        for (i in 0 until size-1){
//            if (reds[i][0] == reds[i+1][0]){
//                for (k in ny(reds[i][1]) to ny(reds[i+1][1])){
//
//                }
//            }
//        }
//
//
//        for (i in 0 until size) {
//            for (j in i + 1 until size) {
//
//                val width = abs(reds[i][0] - reds[j][0]) + 1
//                val height = abs(reds[i][1] - reds[j][1]) + 1
//                val area = width * height
//                if (area > largestArea) {
//                    largestArea = area
//                }
//            }
//        }
//        return largestArea
//    }