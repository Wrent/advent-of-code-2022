package cz.wrent.advent

import java.util.*

fun main() {
	val result = partOne(input)
	println("5a: $result")
	val resultTwo = partTwo(input)
	println("5b: $resultTwo")
}

private fun partOne(input: String): String {
	return moveCrates(input) { x, y, z -> moveCrates9000(x, y, z) }
}

private fun partTwo(input: String): String {
	return moveCrates(input) { x, y, z -> moveCrates9001(x, y, z) }
}

private fun moveCrates(input: String, fn: (from: Deque<String>, to: Deque<String>, num: Int) -> Unit): String {
	val (stacksInput, movesInput) = input.split("\n\n")
	val stacks = stacksInput.toStacks()
	val moves = movesInput.toMoves()
	moves.forEach {
		val from = stacks.get(it.from)
		val to = stacks.get(it.to)
		fn(from, to, it.num)
	}
	return stacks.map { it.peek() }.joinToString(separator = "")
}

private fun moveCrates9000(from: Deque<String>, to: Deque<String>, num: Int) {
	repeat(num) {
		val crate = from.pop()
		to.push(crate)
	}
}

private fun moveCrates9001(from: Deque<String>, to: Deque<String>, num: Int) {
	val crates = mutableListOf<String>()
	repeat(num) {
		crates.add(from.pop())
	}
	crates.reversed().forEach { to.push(it) }
}

private fun String.toStacks(): List<Deque<String>> {
	val split = this.split("\n").dropLast(1)
	val cnt = (split.first().length + 1) / 4
	val lists = (0 until cnt).map { mutableListOf<String>() }
	for (row in split) {
		var i = 1
		var j = 0
		while (i < row.length) {
			val char = row[i]
			if (char != ' ') {
				lists.get(j).add(char.toString())
			}
			j++
			i += 4
		}
	}
	return lists.map {
		it.toDeque()
	}
}

private fun List<String>.toDeque(): Deque<String> {
	return LinkedList(this)
}

private data class Move(val num: Int, val from: Int, val to: Int)

private fun String.toMoves(): List<Move> {
	val regex =  Regex("move (\\d+) from (\\d+) to (\\d+)")
	return this.split("\n")
		.map { regex.matchEntire(it) }
		.map { Move(it!!.groupValues.get(1).toInt(), it.groupValues.get(2).toInt() - 1, it.groupValues.get(3).toInt() - 1) }
}

private const val input = """[Q] [J]                         [H]
[G] [S] [Q]     [Z]             [P]
[P] [F] [M]     [F]     [F]     [S]
[R] [R] [P] [F] [V]     [D]     [L]
[L] [W] [W] [D] [W] [S] [V]     [G]
[C] [H] [H] [T] [D] [L] [M] [B] [B]
[T] [Q] [B] [S] [L] [C] [B] [J] [N]
[F] [N] [F] [V] [Q] [Z] [Z] [T] [Q]
 1   2   3   4   5   6   7   8   9 

move 1 from 8 to 1
move 1 from 6 to 1
move 3 from 7 to 4
move 3 from 2 to 9
move 11 from 9 to 3
move 1 from 6 to 9
move 15 from 3 to 9
move 5 from 2 to 3
move 3 from 7 to 5
move 6 from 9 to 3
move 6 from 1 to 6
move 2 from 3 to 7
move 5 from 4 to 5
move 7 from 9 to 4
move 2 from 9 to 5
move 10 from 4 to 2
move 6 from 5 to 4
move 2 from 7 to 6
move 10 from 2 to 3
move 21 from 3 to 5
move 1 from 3 to 6
move 3 from 6 to 9
move 1 from 8 to 9
move 5 from 4 to 5
move 4 from 9 to 3
move 17 from 5 to 1
move 1 from 6 to 2
move 16 from 5 to 1
move 3 from 3 to 6
move 6 from 6 to 4
move 1 from 2 to 4
move 4 from 1 to 2
move 2 from 6 to 2
move 28 from 1 to 3
move 1 from 9 to 7
move 1 from 8 to 7
move 1 from 5 to 4
move 1 from 2 to 6
move 1 from 3 to 1
move 3 from 2 to 5
move 1 from 6 to 3
move 4 from 4 to 7
move 5 from 5 to 2
move 1 from 5 to 6
move 6 from 1 to 3
move 1 from 6 to 2
move 26 from 3 to 6
move 2 from 7 to 9
move 4 from 7 to 3
move 19 from 6 to 3
move 6 from 2 to 4
move 5 from 3 to 2
move 1 from 9 to 7
move 26 from 3 to 8
move 6 from 4 to 3
move 1 from 3 to 8
move 1 from 6 to 7
move 6 from 3 to 6
move 6 from 6 to 4
move 1 from 9 to 2
move 2 from 4 to 9
move 22 from 8 to 2
move 2 from 6 to 5
move 1 from 9 to 1
move 1 from 6 to 5
move 1 from 7 to 5
move 3 from 6 to 7
move 2 from 6 to 1
move 1 from 1 to 5
move 3 from 5 to 9
move 4 from 8 to 4
move 2 from 1 to 4
move 18 from 2 to 1
move 2 from 7 to 8
move 3 from 9 to 5
move 8 from 1 to 9
move 5 from 9 to 3
move 1 from 9 to 8
move 2 from 9 to 4
move 2 from 7 to 8
move 5 from 5 to 7
move 1 from 9 to 3
move 4 from 8 to 4
move 1 from 7 to 8
move 4 from 4 to 3
move 2 from 8 to 3
move 1 from 8 to 9
move 2 from 1 to 8
move 3 from 4 to 5
move 1 from 8 to 4
move 1 from 9 to 3
move 1 from 8 to 5
move 8 from 1 to 8
move 11 from 2 to 9
move 12 from 3 to 5
move 1 from 3 to 9
move 1 from 8 to 5
move 11 from 9 to 3
move 4 from 5 to 9
move 3 from 8 to 7
move 3 from 7 to 8
move 1 from 5 to 8
move 7 from 4 to 3
move 1 from 4 to 5
move 1 from 2 to 8
move 3 from 7 to 6
move 3 from 4 to 8
move 1 from 7 to 9
move 2 from 4 to 7
move 5 from 8 to 1
move 3 from 6 to 5
move 2 from 4 to 2
move 1 from 9 to 4
move 1 from 8 to 6
move 1 from 2 to 9
move 1 from 8 to 5
move 3 from 8 to 4
move 3 from 4 to 2
move 4 from 3 to 9
move 17 from 5 to 9
move 9 from 9 to 6
move 1 from 9 to 3
move 5 from 6 to 3
move 3 from 6 to 3
move 8 from 9 to 5
move 2 from 8 to 5
move 1 from 4 to 8
move 1 from 5 to 3
move 1 from 8 to 5
move 3 from 2 to 6
move 3 from 1 to 4
move 7 from 5 to 1
move 1 from 2 to 6
move 13 from 3 to 6
move 2 from 7 to 8
move 13 from 6 to 5
move 3 from 5 to 7
move 6 from 5 to 6
move 1 from 7 to 6
move 2 from 7 to 3
move 1 from 6 to 8
move 13 from 3 to 5
move 9 from 5 to 9
move 7 from 5 to 7
move 17 from 9 to 2
move 3 from 4 to 7
move 9 from 2 to 9
move 10 from 9 to 3
move 8 from 7 to 8
move 2 from 5 to 3
move 4 from 2 to 6
move 11 from 3 to 9
move 9 from 6 to 5
move 5 from 9 to 8
move 1 from 3 to 1
move 3 from 9 to 1
move 2 from 5 to 2
move 1 from 7 to 9
move 2 from 9 to 4
move 2 from 9 to 8
move 13 from 1 to 8
move 3 from 8 to 5
move 27 from 8 to 1
move 10 from 5 to 9
move 1 from 7 to 2
move 2 from 4 to 3
move 10 from 9 to 6
move 1 from 8 to 7
move 15 from 1 to 9
move 13 from 9 to 5
move 15 from 5 to 7
move 5 from 1 to 3
move 8 from 7 to 1
move 7 from 7 to 1
move 16 from 1 to 8
move 4 from 3 to 9
move 4 from 1 to 7
move 4 from 9 to 6
move 5 from 2 to 7
move 15 from 8 to 6
move 1 from 9 to 1
move 3 from 3 to 4
move 1 from 9 to 7
move 1 from 2 to 7
move 1 from 2 to 7
move 1 from 8 to 1
move 3 from 4 to 8
move 3 from 8 to 1
move 8 from 6 to 8
move 7 from 1 to 4
move 11 from 6 to 8
move 14 from 6 to 5
move 13 from 8 to 7
move 4 from 7 to 5
move 15 from 7 to 4
move 6 from 5 to 4
move 2 from 5 to 9
move 1 from 5 to 2
move 3 from 8 to 5
move 19 from 4 to 7
move 10 from 5 to 8
move 2 from 6 to 8
move 1 from 4 to 8
move 2 from 7 to 9
move 9 from 7 to 4
move 6 from 4 to 6
move 11 from 4 to 8
move 2 from 5 to 4
move 5 from 6 to 4
move 1 from 6 to 7
move 3 from 9 to 5
move 3 from 8 to 5
move 3 from 7 to 6
move 11 from 8 to 7
move 1 from 9 to 5
move 1 from 6 to 8
move 1 from 2 to 1
move 5 from 4 to 9
move 2 from 4 to 1
move 2 from 1 to 4
move 1 from 1 to 9
move 4 from 5 to 1
move 1 from 4 to 6
move 17 from 7 to 5
move 9 from 8 to 7
move 6 from 9 to 7
move 3 from 1 to 9
move 12 from 7 to 9
move 12 from 9 to 5
move 5 from 7 to 9
move 17 from 5 to 3
move 7 from 3 to 1
move 5 from 1 to 5
move 5 from 9 to 2
move 4 from 3 to 5
move 1 from 4 to 8
move 5 from 2 to 1
move 22 from 5 to 9
move 3 from 7 to 6
move 6 from 6 to 9
move 2 from 5 to 4
move 1 from 6 to 3
move 2 from 4 to 1
move 3 from 8 to 2
move 1 from 3 to 4
move 24 from 9 to 1
move 4 from 3 to 9
move 2 from 2 to 9
move 2 from 3 to 1
move 1 from 8 to 6
move 1 from 6 to 9
move 1 from 8 to 9
move 2 from 7 to 4
move 1 from 8 to 3
move 1 from 4 to 7
move 3 from 9 to 8
move 1 from 2 to 1
move 9 from 9 to 3
move 1 from 8 to 7
move 1 from 4 to 3
move 2 from 9 to 7
move 1 from 9 to 3
move 2 from 8 to 4
move 12 from 3 to 8
move 2 from 1 to 7
move 1 from 4 to 3
move 30 from 1 to 5
move 6 from 5 to 7
move 12 from 7 to 2
move 1 from 3 to 4
move 2 from 1 to 3
move 1 from 4 to 9
move 10 from 5 to 7
move 10 from 2 to 6
move 8 from 8 to 3
move 3 from 1 to 3
move 5 from 6 to 3
move 2 from 8 to 5
move 1 from 9 to 2
move 2 from 8 to 6
move 4 from 7 to 2
move 3 from 2 to 7
move 2 from 7 to 5
move 1 from 4 to 9
move 11 from 3 to 1
move 7 from 6 to 9
move 3 from 2 to 3
move 10 from 1 to 7
move 14 from 7 to 5
move 3 from 7 to 6
move 5 from 9 to 7
move 29 from 5 to 7
move 6 from 3 to 9
move 2 from 9 to 7
move 15 from 7 to 5
move 11 from 5 to 6
move 5 from 9 to 5
move 10 from 5 to 8
move 1 from 2 to 4
move 1 from 8 to 2
move 2 from 4 to 3
move 2 from 5 to 9
move 8 from 8 to 9
move 11 from 9 to 3
move 1 from 1 to 8
move 18 from 7 to 3
move 1 from 9 to 3
move 28 from 3 to 5
move 12 from 6 to 7
move 1 from 2 to 9
move 15 from 7 to 2
move 1 from 8 to 1
move 10 from 2 to 9
move 10 from 5 to 3
move 2 from 2 to 3
move 18 from 3 to 4
move 6 from 9 to 4
move 1 from 1 to 7
move 1 from 6 to 4
move 1 from 8 to 2
move 1 from 9 to 4
move 2 from 9 to 4
move 19 from 4 to 3
move 1 from 7 to 9
move 1 from 9 to 7
move 1 from 6 to 8
move 3 from 2 to 8
move 2 from 9 to 5
move 15 from 3 to 1
move 7 from 5 to 1
move 3 from 4 to 9
move 1 from 7 to 2
move 3 from 3 to 1
move 6 from 5 to 2
move 3 from 3 to 9
move 4 from 9 to 2
move 5 from 5 to 3
move 1 from 3 to 5
move 3 from 5 to 7
move 3 from 8 to 5
move 1 from 7 to 5
move 4 from 5 to 1
move 4 from 4 to 2
move 2 from 7 to 8
move 12 from 1 to 6
move 1 from 8 to 6
move 6 from 2 to 3
move 9 from 3 to 8
move 1 from 3 to 4
move 3 from 6 to 1
move 2 from 9 to 2
move 1 from 4 to 5
move 2 from 8 to 3
move 10 from 2 to 1
move 2 from 4 to 7
move 12 from 1 to 4
move 1 from 5 to 1
move 7 from 4 to 9
move 2 from 3 to 2
move 6 from 9 to 2
move 1 from 9 to 1
move 1 from 7 to 8
move 5 from 6 to 7
move 3 from 6 to 1
move 6 from 2 to 3
move 2 from 4 to 3
move 1 from 6 to 8
move 1 from 6 to 7
move 8 from 3 to 9
move 2 from 4 to 5
move 3 from 2 to 4
move 10 from 8 to 2
move 22 from 1 to 9
move 9 from 2 to 4
move 1 from 1 to 3
move 1 from 3 to 2
move 3 from 2 to 4
move 2 from 7 to 1
move 14 from 4 to 2
move 2 from 1 to 8
move 2 from 4 to 5
move 4 from 7 to 8
move 24 from 9 to 6
move 3 from 5 to 9
move 1 from 9 to 8
move 1 from 5 to 2
move 1 from 6 to 7
move 6 from 9 to 1
move 1 from 7 to 3
move 5 from 8 to 6
move 9 from 6 to 3
move 4 from 1 to 4
move 2 from 1 to 2
move 11 from 6 to 3
move 13 from 3 to 2
move 2 from 9 to 8
move 8 from 3 to 8
move 2 from 8 to 5
move 1 from 7 to 5
move 3 from 6 to 3
move 11 from 8 to 5
move 13 from 2 to 4
move 10 from 5 to 2
move 2 from 3 to 4
move 2 from 5 to 7
move 15 from 4 to 9
move 2 from 7 to 4
move 2 from 4 to 2
move 2 from 4 to 9
move 2 from 4 to 2
move 1 from 3 to 8
move 1 from 8 to 1
move 1 from 1 to 2
move 1 from 6 to 3
move 7 from 2 to 4
move 1 from 5 to 3
move 7 from 9 to 1
move 7 from 1 to 2
move 4 from 6 to 9
move 12 from 9 to 7
move 6 from 7 to 5
move 1 from 3 to 5
move 7 from 4 to 7
move 3 from 7 to 8
move 3 from 8 to 6
move 18 from 2 to 9
move 7 from 2 to 3
move 15 from 9 to 4
move 3 from 3 to 9
move 1 from 3 to 1
move 3 from 5 to 4
move 1 from 1 to 2
move 1 from 9 to 2
move 2 from 6 to 2
move 5 from 7 to 6
move 5 from 2 to 7
move 3 from 3 to 4
move 5 from 5 to 3
move 6 from 7 to 4
move 9 from 4 to 2
move 18 from 4 to 9
move 6 from 2 to 1
move 1 from 1 to 9
move 4 from 7 to 4
move 7 from 2 to 4
move 1 from 2 to 8
move 1 from 4 to 2
move 4 from 3 to 4
move 16 from 9 to 5
move 9 from 9 to 8
move 1 from 9 to 7
move 4 from 1 to 2
move 2 from 5 to 4
move 10 from 5 to 4
move 4 from 2 to 1
move 5 from 1 to 2
move 1 from 8 to 5
move 1 from 6 to 5
move 4 from 8 to 5
move 2 from 6 to 9
move 3 from 6 to 2
move 2 from 9 to 1
move 1 from 7 to 6
move 1 from 3 to 8
move 9 from 5 to 9
move 4 from 8 to 1
move 2 from 8 to 2
move 1 from 5 to 7
move 9 from 9 to 8
move 1 from 7 to 5
move 9 from 8 to 2
move 6 from 1 to 6
move 6 from 2 to 6
move 10 from 2 to 5
move 5 from 2 to 1
move 1 from 3 to 5
move 8 from 5 to 4
move 5 from 1 to 3
move 10 from 6 to 8
move 3 from 6 to 9
move 4 from 3 to 1
move 5 from 8 to 2
move 4 from 5 to 9
move 1 from 3 to 7
move 1 from 7 to 3
move 1 from 8 to 6
move 1 from 6 to 1
move 15 from 4 to 8
move 5 from 9 to 2
move 1 from 9 to 1
move 1 from 1 to 3
move 6 from 4 to 8
move 12 from 8 to 7
move 1 from 3 to 5
move 3 from 1 to 9
move 13 from 4 to 9
move 5 from 7 to 2
move 1 from 5 to 4
move 8 from 9 to 5
move 6 from 2 to 5
move 2 from 5 to 6"""
