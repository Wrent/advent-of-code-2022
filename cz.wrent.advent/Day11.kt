package cz.wrent.advent

fun main() {
	val result = partOne(input, 20, 3)
	println("10a: $result")
	println("10b: ${partOne(input, 10000, 1)}")
}

private fun partOne(input: String, rounds: Int, divisor: Long): Long {
	val monkeys = input.split("\n\n").map { it.parseMonkey() }
	for (i in 1..rounds) {
		monkeys.forEach { it.turn(monkeys, divisor) }
	}
	val sortedMonkeys = monkeys.sortedByDescending { it.inspections }
	return (sortedMonkeys.get(0).inspections * sortedMonkeys.get(1).inspections).toLong()
}

private class Monkey(
	val operation: (Long) -> Long,
	val testDivisor: Long,
	val trueMonkey: Int,
	val falseMonkey: Int,
) {
	val items = mutableListOf<Long>()
	var inspections = 0

	fun add(item: Long) {
		items.add(item)
	}

	fun turn(monkeys: List<Monkey>, divisor: Long) {
		items.forEach {
			val worry = operation(it) / divisor
			if (worry % testDivisor == 0L) {
				monkeys[trueMonkey].add(worry)
			} else {
				monkeys[falseMonkey].add(worry)
			}
			inspections++
		}
		items.clear()
	}
}

private fun String.parseMonkey(): Monkey {
	val lines = this.split("\n")
	val startingItems = lines.get(1).replace("  Starting items: ", "").split(", ").map { it.toLong() }
	val operationData = lines.get(2).replace("  Operation: new = old ", "").split(" ")
	val operationNum = if (operationData.get(1) == "old") null else operationData.get(1).toLong()
	val operation: (Long) -> Long = if (operationData.first() == "*") { { it * (operationNum ?: it) } } else { {it + (operationNum ?: it)} }
	val testDivisor = lines.get(3).replace("  Test: divisible by ", "").toLong()
	val trueMonkey = lines.get(4).replace("    If true: throw to monkey ", "").toInt()
	val falseMonkey = lines.get(5).replace("    If false: throw to monkey ", "").toInt()
	val monkey = Monkey(operation, testDivisor, trueMonkey, falseMonkey)
	startingItems.forEach { monkey.add(it) }
	return monkey
}

private const val input = """Monkey 0:
  Starting items: 66, 79
  Operation: new = old * 11
  Test: divisible by 7
    If true: throw to monkey 6
    If false: throw to monkey 7

Monkey 1:
  Starting items: 84, 94, 94, 81, 98, 75
  Operation: new = old * 17
  Test: divisible by 13
    If true: throw to monkey 5
    If false: throw to monkey 2

Monkey 2:
  Starting items: 85, 79, 59, 64, 79, 95, 67
  Operation: new = old + 8
  Test: divisible by 5
    If true: throw to monkey 4
    If false: throw to monkey 5

Monkey 3:
  Starting items: 70
  Operation: new = old + 3
  Test: divisible by 19
    If true: throw to monkey 6
    If false: throw to monkey 0

Monkey 4:
  Starting items: 57, 69, 78, 78
  Operation: new = old + 4
  Test: divisible by 2
    If true: throw to monkey 0
    If false: throw to monkey 3

Monkey 5:
  Starting items: 65, 92, 60, 74, 72
  Operation: new = old + 7
  Test: divisible by 11
    If true: throw to monkey 3
    If false: throw to monkey 4

Monkey 6:
  Starting items: 77, 91, 91
  Operation: new = old * old
  Test: divisible by 17
    If true: throw to monkey 1
    If false: throw to monkey 7

Monkey 7:
  Starting items: 76, 58, 57, 55, 67, 77, 54, 99
  Operation: new = old + 6
  Test: divisible by 3
    If true: throw to monkey 2
    If false: throw to monkey 1"""
