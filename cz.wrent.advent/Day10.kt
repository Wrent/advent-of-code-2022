package cz.wrent.advent

fun main() {
	println(partOne(test))

	val result = partOne(input)
	println("10a: $result")

	partTwo(test)
	partTwo(input)
}

private fun partOne(input: String): Long {
	val states = getStates(input)
	return states
		.filter { it.cycle == 19L || it.cycle == 59L || it.cycle == 99L || it.cycle == 139L || it.cycle == 179L || it.cycle == 219L }
		.sumOf { it.x * (it.cycle + 1) }
}

private fun getStates(input: String): MutableList<State> {
	val commands = input.split("\n").map { it.parseCommand() }

	val states = mutableListOf(State())

	commands.forEach {
		states.addAll(it.run(states.last()))
	}
	return states
}

private fun partTwo(input: String) {
	val states = getStates(input)

	states.forEachIndexed { i, state ->
		val pos = i % 40
		if (pos == 0) {
			print("\n")
		}
		if (state.x in (pos - 1)..(pos + 1)) {
			print("#")
		} else {
			print(".")
		}

	}
}

private fun String.parseCommand(): Command {
	val split = this.split(" ")
	return when (split.first()) {
		"noop" -> NoOp()
		"addx" -> AddX(split.get(1).toLong())
		else -> error("Unknown command")
	}
}

private interface Command {
	fun run(state: State): List<State>
	fun print()
}

private class NoOp : Command {
	override fun run(state: State): List<State> {
		return listOf(state.copy(cycle = state.cycle + 1))
	}

	override fun print() {
		println("noop")
	}
}

private class AddX(val add: Long) : Command {
	override fun run(state: State): List<State> {
		return listOf(state.copy(cycle = state.cycle + 1), State(x = state.x + add, cycle = state.cycle + 2))
	}

	override fun print() {
		println("addx $add")
	}
}

private data class State(
	var x: Long = 1,
	var cycle: Long = 0
)

private const val test = """addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop"""

private const val input =
	"""noop
addx 5
noop
noop
noop
addx 1
addx 2
addx 5
addx 2
addx 5
noop
noop
noop
noop
noop
addx -12
addx 18
addx -1
noop
addx 3
addx 5
addx -5
addx 7
noop
addx -36
addx 18
addx -16
noop
noop
noop
addx 5
addx 2
addx 5
addx 2
addx 13
addx -6
addx -4
addx 5
addx 2
addx 4
addx -3
addx 2
noop
addx 3
addx 2
addx 5
addx -40
addx 25
addx -22
addx 25
addx -21
addx 5
addx 3
noop
addx 2
addx 19
addx -10
addx -4
noop
addx -4
addx 7
noop
addx 3
addx 2
addx 5
addx 2
addx -26
addx 27
addx -36
noop
noop
noop
noop
addx 4
addx 6
noop
addx 12
addx -11
addx 2
noop
noop
noop
addx 5
addx 5
addx 2
noop
noop
addx 1
addx 2
addx 5
addx 2
addx 1
noop
noop
addx -38
noop
addx 9
addx -4
noop
noop
addx 7
addx 10
addx -9
addx 2
noop
addx -9
addx 14
addx 5
addx 2
addx -24
addx 25
addx 2
addx 5
addx 2
addx -30
addx 31
addx -38
addx 7
noop
noop
noop
addx 1
addx 21
addx -16
addx 8
addx -4
addx 2
addx 3
noop
noop
addx 5
addx -2
addx 5
addx 3
addx -1
addx -1
addx 4
addx 5
addx -38
noop"""
