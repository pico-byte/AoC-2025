fun main() {
    data class Shape(val id: Int, val cells: List<Pair<Int, Int>>)
    data class Region(val width: Int, val height: Int, val counts: List<Int>)

    fun parseInput(input: List<String>): Pair<List<List<List<Pair<Int, Int>>>>, List<Region>> {
        val shapes = mutableListOf<List<String>>()
        var i = 0
        val regionLineRegex = Regex("^\\d+x\\d+:.*")
        while (i < input.size && input[i].contains(":")) {
            if (regionLineRegex.matches(input[i].trim())) break
            val shape = mutableListOf<String>()
            i++
            while (i < input.size && input[i].isNotBlank() && !input[i].contains(":") && !regionLineRegex.matches(input[i].trim())) {
                shape.add(input[i])
                i++
            }
            if (shape.isNotEmpty()) shapes.add(shape)
            while (i < input.size && input[i].isBlank()) i++
        }
        val regions = mutableListOf<Region>()
        while (i < input.size) {
            val line = input[i].trim()
            if (line.isBlank()) { i++; continue }
            if (!regionLineRegex.matches(line)) { i++; continue }
            val (size, rest) = line.split(":")
            val (w, h) = size.split("x").map { it.toInt() }
            val counts = rest.trim().split(" ").map { it.toInt() }
            regions.add(Region(w, h, counts))
            i++
        }
        fun allOrientations(shape: List<String>): List<List<Pair<Int, Int>>> {
            val variants = mutableSetOf<Set<Pair<Int, Int>>>()
            val h = shape.size
            val w = shape[0].length
            fun norm(cells: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
                val minX = cells.minOf { it.first }
                val minY = cells.minOf { it.second }
                return cells.map { (x, y) -> x - minX to y - minY }.sortedWith(compareBy({ it.first }, { it.second }))
            }
            var grids = listOf(shape)
            repeat(3) { grids = grids + listOf(grids.last().mapIndexed { y, row -> grids.last().indices.map { x -> grids.last()[grids.last().size - 1 - x][y] }.joinToString("") }) }
            grids = grids + grids.map { it.map { row -> row.reversed() } }
            for (g in grids) {
                val cells = mutableListOf<Pair<Int, Int>>()
                for (y in g.indices) for (x in g[y].indices) if (g[y][x] == '#') cells.add(x to y)
                variants.add(norm(cells).toSet())
            }
            return variants.map { it.toList() }
        }
        val allShapeOrientations = shapes.map { allOrientations(it) }
        return allShapeOrientations to regions
    }

    fun canFit(region: Region, allShapeOrientations: List<List<List<Pair<Int, Int>>>>, counts: List<Int>): Boolean {
        val totalPresentArea = counts.indices.sumOf { idx ->
            if (allShapeOrientations[idx].isEmpty()) 0 else allShapeOrientations[idx][0].size * counts[idx]
        }
        val regionArea = region.width * region.height
        if (totalPresentArea > regionArea) return false

        val grid = Array(region.height) { BooleanArray(region.width) }
        val totalPresents = counts.sum()
        val presentTypes = counts.indices.flatMap { idx -> List(counts[idx]) { idx } }
        fun place(placed: Int, remainingCounts: List<Int>): Boolean {
            if (placed == totalPresents) return true
            for (type in counts.indices) {
                if (remainingCounts[type] == 0) continue
                val orientations = allShapeOrientations[type]
                for (ori in orientations) {
                    val maxX = ori.maxOf { it.first }
                    val maxY = ori.maxOf { it.second }
                    for (y in 0..region.height - maxY - 1) {
                        for (x in 0..region.width - maxX - 1) {
                            if (ori.all { (dx, dy) -> !grid[y + dy][x + dx] }) {
                                ori.forEach { (dx, dy) -> grid[y + dy][x + dx] = true }
                                val newCounts = remainingCounts.toMutableList(); newCounts[type]--
                                if (place(placed + 1, newCounts)) return true
                                ori.forEach { (dx, dy) -> grid[y + dy][x + dx] = false }
                            }
                        }
                    }
                }
            }
            return false
        }
        return place(0, counts)
    }

    fun part1(input: List<String>): Long {
        val (allShapeOrientations, regions) = parseInput(input)
        var count = 0L
        for (region in regions) {
            if (canFit(region, allShapeOrientations, region.counts)) count++
        }
        return count
    }

    fun part2(input: List<String>): Long {
        //YAAAY! No part 2 :)
        return 0L
    }

    val input = readInput("input")
    part1(input).println()
    part2(input).println()
}
