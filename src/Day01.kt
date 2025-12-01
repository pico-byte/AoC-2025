
fun main() {
    fun part1(input: List<String>): Int {
        var tracker: Int = 50
        var output = 0
        for (i in 0..<input.size){
            val number = input[i].substring(1).toInt()
            if (input[i][0] == 'L'){
                tracker -= number
                tracker %= 100
            }else {
                tracker += number
                tracker %= 100
            }
            if (tracker == 0){
                output++
            }
        }
        return output
    }

    fun part2(input: List<String>): Int {
        var tracker = 50
        var output = 0

        fun wrap(x: Int) = ((x % 100) + 100) % 100

        for (cmd in input) {
            val dir = cmd[0]
            val dist = cmd.substring(1).toInt()

            repeat(dist) {
                tracker = if (dir == 'L') wrap(tracker - 1) else wrap(tracker + 1)
                if (tracker == 0) output++
            }
        }

        return output
    }


    fun tested(input: List<String>): List<String> {
        return input
    }

    // Read the input from the `src/input.txt` file.
    val input = readInput("input")
    part1(input).println()
    part2(input).println()
    //tested(input).println()
}
