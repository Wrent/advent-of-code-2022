package cz.wrent.advent

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
	println(partOne(test, 10))
	val result = partOne(input, 2000000)
	println("15a: $result")
	println(partTwo(test, 20))
	println("15b: ${partTwo(input, 4000000)}")
}

private fun partOne(input: String, row: Int): Int {
	val map = input.parse()
	val sensors = map.flatMap { r -> r.value.filter { it.value > 0 }.map { (it.key to r.key) to it.value } }
	val beacons = map.flatMap { r -> r.value.filter { it.value == BEACON }.map { it.key to r.key } }.toSet()
	val set = mutableSetOf<Pair<Int, Int>>()
	sensors.forEach { (coord, dist) ->
		set.addAll(getCleared(coord, dist, row))
	}
	set.removeAll(beacons)
	return set.size
}

private fun partTwo(input: String, limit: Int): Long {
	val map = input.parse()
	val sensors = map.flatMap { r -> r.value.filter { it.value > 0 }.map { (it.key to r.key) to it.value } }
	for (i in 0..limit) {
		val notCleared = sensors.map { (coord, dist) ->
			getCleared(coord, dist, i, limit)
		}.getNotCleared()
		if (notCleared != null) {
			return notCleared.toLong() * 4000000 + i.toLong()
		}
	}
	return -1
}

private fun getCleared(coord: Pair<Int, Int>, dist: Int, row: Int): Set<Pair<Int, Int>> {
	val distToRow = abs(coord.second - row)
	val remainingDist = dist - distToRow
	return (coord.first - remainingDist..coord.first + remainingDist).map {
		it to row
	}.toSet()
}

private fun getCleared(coord: Pair<Int, Int>, dist: Int, row: Int, limit: Int): IntRange {
	val distToRow = abs(coord.second - row)
	val remainingDist = dist - distToRow
	if (remainingDist < 0) {
		return IntRange.EMPTY
	}
	return (max(0, coord.first - remainingDist)..min(coord.first + remainingDist, limit))
}

private fun List<IntRange>.getNotCleared(): Int? {
	val sorted = this.sortedBy { it.start }
	var current = sorted.first()
	sorted.drop(1).forEach {
		if (!current.contains(it.start)) {
			return it.start - 1
		}
		if (it.endInclusive > current.endInclusive) {
			current = current.start .. it.endInclusive
		}
	}
	return null
}

//private fun getNotCleared(coord: Pair<Int, Int>, dist: Int, row: Int, max: Int): List<IntRange> {
//	val distToRow = abs(coord.second - row)
//	val remainingDist = dist - distToRow
//	return listOf(0 until coord.first - remainingDist, coord.first + remainingDist until max) // todo ta nula tam asi hapruje
//}

//private fun Map<Int, Map<Int, Int>>.fill(): Map<Int, Map<Int, Int>> {
//	val clear = mutableMapOf<Int, MutableMap<Int, Int>>()
//	this.forEach { (y, row) ->
//		row.forEach { (x, value) ->
//				if (value > 0) {
//					addAllClears(x to y, value, clear)
//				}
//		}
//	}
//	this.forEach { (y, row) ->
//		row.forEach { (x, value) ->
//			clear.computeIfAbsent(y) { mutableMapOf() }[x] = value
//		}
//	}
//	return clear
//}
//
//private fun addAllClears(from: Pair<Int, Int>, value: Int, to: MutableMap<Int, MutableMap<Int, Int>>) {
//	val remaining: Deque<Pair<Pair<Int, Int>, Int>> = LinkedList(listOf(from to 0))
//	while (remaining.isNotEmpty()) {
//		val curr = remaining.pop()
//		val coord = curr.first
//		val next = curr.second
//		if (next <= value) {
//			to.computeIfAbsent(coord.second) { mutableMapOf() }[coord.first] = CLEAR
//			curr.first.toNeighbours().map { it to next + 1 }
//				.forEach {
//					remaining.push(it)
//				}
//		}
//	}
//}

private fun String.parse(): Map<Int, Map<Int, Int>> {
	val map = mutableMapOf<Int, MutableMap<Int, Int>>()
	val regex = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")
	this.split("\n")
		.map { regex.matchEntire(it) }
		.filterNotNull()
		.map { it.groupValues }
		.forEach {
			val beacon = it.get(3).toInt() to it.get(4).toInt()
			val sensor = it.get(1).toInt() to it.get(2).toInt()
			map.computeIfAbsent(sensor.second) { mutableMapOf() }[sensor.first] = dist(sensor, beacon)
			map.computeIfAbsent(beacon.second) { mutableMapOf() }[beacon.first] = BEACON
		}
	return map
}

private fun dist(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
	return abs(a.first - b.first) + abs(a.second - b.second)
}

private const val BEACON = -1
private const val CLEAR = 0

private const val test = """Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3"""

private const val input =
	"""Sensor at x=3844106, y=3888618: closest beacon is at x=3225436, y=4052707
Sensor at x=1380352, y=1857923: closest beacon is at x=10411, y=2000000
Sensor at x=272, y=1998931: closest beacon is at x=10411, y=2000000
Sensor at x=2119959, y=184595: closest beacon is at x=2039500, y=-250317
Sensor at x=1675775, y=2817868: closest beacon is at x=2307516, y=3313037
Sensor at x=2628344, y=2174105: closest beacon is at x=3166783, y=2549046
Sensor at x=2919046, y=3736158: closest beacon is at x=3145593, y=4120490
Sensor at x=16, y=2009884: closest beacon is at x=10411, y=2000000
Sensor at x=2504789, y=3988246: closest beacon is at x=3145593, y=4120490
Sensor at x=2861842, y=2428768: closest beacon is at x=3166783, y=2549046
Sensor at x=3361207, y=130612: closest beacon is at x=2039500, y=-250317
Sensor at x=831856, y=591484: closest beacon is at x=-175938, y=1260620
Sensor at x=3125600, y=1745424: closest beacon is at x=3166783, y=2549046
Sensor at x=21581, y=3243480: closest beacon is at x=10411, y=2000000
Sensor at x=2757890, y=3187285: closest beacon is at x=2307516, y=3313037
Sensor at x=3849488, y=2414083: closest beacon is at x=3166783, y=2549046
Sensor at x=3862221, y=757146: closest beacon is at x=4552923, y=1057347
Sensor at x=3558604, y=2961030: closest beacon is at x=3166783, y=2549046
Sensor at x=3995832, y=1706663: closest beacon is at x=4552923, y=1057347
Sensor at x=1082213, y=3708082: closest beacon is at x=2307516, y=3313037
Sensor at x=135817, y=1427041: closest beacon is at x=-175938, y=1260620
Sensor at x=2467372, y=697908: closest beacon is at x=2039500, y=-250317
Sensor at x=3448383, y=3674287: closest beacon is at x=3225436, y=4052707"""
