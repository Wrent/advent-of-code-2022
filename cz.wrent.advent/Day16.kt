package cz.wrent.advent

import java.lang.Integer.max
import java.util.Deque
import java.util.LinkedList

fun main() {
//	println(partOne(test))
//	val result = partOne(input)
//	println("16a: $result")
	println(partTwo(test))
	println("16b: ${partTwo(input)}")
}

private fun partOne(input: String): Int {
	val valves = input.toValves()
	calculateDistances(valves)

	return recursiveSolution(valves)
}

private fun recursiveSolution(valves: Map<String, Valve>): Int {
	val meaningful = valves.values.filter { it.flowRate > 0 }.sortedByDescending { it.flowRate }.map { it.label }

	return meaningful.map { process("AA", it, 30, 0, emptyList(), meaningful, valves) }.maxOf { it }
}

private fun process(
	current: String,
	next: String,
	minutes: Int,
	released: Int,
	opened: List<String>,
	meaningful: List<String>,
	valves: Map<String, Valve>
): Int {
	val distance = valves[current]!!.distances[next]!!
	val time = minutes - distance - 1

	if (time < 1) {
		return released
	}
	val nextReleased = released + time * valves[next]!!.flowRate
	val nextOpened = opened + next

	if (meaningful.size == nextOpened.size) {
		return nextReleased
	} else {
		return meaningful.filterNot { nextOpened.contains(it) }
			.map { process(next, it, time, nextReleased, nextOpened, meaningful, valves) }.maxOf { it }
	}
}

fun <T> List<T>.permutations(): List<List<T>> =
	if (isEmpty()) listOf(emptyList()) else mutableListOf<List<T>>().also { result ->
		for (i in this.indices) {
			(this - this[i]).permutations().forEach {
				result.add(it + this[i])
			}
		}
	}

private fun calculateDistances(valves: Map<String, Valve>) {
	valves.values.filter { it.label == "AA" || it.flowRate > 0 }.forEach { curr ->
		curr.distances = calculateDistances(curr, valves)
	}
}

private fun calculateDistances(from: Valve, valves: Map<String, Valve>): Map<String, Int> {
	val nextValves: Deque<Pair<String, Int>> = LinkedList(listOf(from.label to 0))
	val map = mutableMapOf<String, Int>()
	while (nextValves.isNotEmpty()) {
		val next = nextValves.pop()
		map[next.first] = next.second
		val nextValve = valves[next.first]!!
		val nextDistance = next.second + 1
		nextValve.leadsTo.forEach {
			if (map[it] == null || map[it]!! > nextDistance)
				nextValves.push(it to next.second + 1)
		}
	}
	return map
}

private fun partTwo(input: String): Int {
	val valves = input.toValves()
	calculateDistances(valves)

	return recursiveSolutionWithElephant(valves)
}

private fun recursiveSolutionWithElephant(valves: Map<String, Valve>): Int {
	val meaningful = valves.values.filter { it.flowRate > 0 }.sortedByDescending { it.flowRate }.map { it.label }

	return meaningful.map { i ->
		meaningful.map { j ->
			if (i == j) -1 else processWithElephant("AA", "AA", i, j, 26, 26, 0, emptyList(), meaningful, valves)
		}.maxOf { it }
	}.maxOf { it }
}

private fun processWithElephant(
	current: String,
	currentElephant: String,
	next: String,
	nextElephant: String,
	minutes: Int,
	elephantMinutes: Int,
	released: Int,
	opened: List<String>,
	meaningful: List<String>,
	valves: Map<String, Valve>
): Int {
	val distance = valves[current]!!.distances[next]!!
	val time = minutes - distance - 1

	val elephantDistance = valves[currentElephant]!!.distances[nextElephant]!!
	val elephantTime = elephantMinutes - elephantDistance - 1

	if (time < 1 && elephantTime < 1) {
		return released
	}
	var nextReleased = released
	var nextOpened = opened
	if (time > 0) {
		nextReleased += time * valves[next]!!.flowRate
		nextOpened = nextOpened + next
	}
	if (elephantTime > 0) {
		nextReleased += elephantTime * valves[nextElephant]!!.flowRate
		nextOpened = nextOpened + nextElephant
	}

	if (meaningful.size == nextOpened.size) {
		if (nextReleased > best) {
			best = nextReleased
			println(best)
		}
		return nextReleased
	} else {
		val filtered = meaningful.filterNot { nextOpened.contains(it) }
		if (!canBeBetterThanBest(nextReleased, max(time, elephantTime), filtered, valves)) {
			return -1
		}

		return filtered.map { i ->
			filtered.map { j ->
				if (i == j) -1 else processWithElephant(
					next,
					nextElephant,
					i,
					j,
					time,
					elephantTime,
					nextReleased,
					nextOpened,
					meaningful,
					valves
				)
			}.maxOf { it }
		}.maxOf { it }
	}
}

private fun canBeBetterThanBest(
	nextReleased: Int,
	max: Int,
	filtered: List<String>,
	valves: Map<String, Valve>
): Boolean {
	val b = nextReleased + filtered.mapIndexed { i, label ->
		valves[label]!!.flowRate * (max - (2 * i))
	}.sumOf { it }
//	println("$best : $b")
	return b > best
}

var best = 1488

private data class Next(
	val label: String,
	val time: Int,
	val released: Int,
	val alreadyReleased: MutableSet<String> = mutableSetOf()
)

private fun String.toValves(): Map<String, Valve> {
	val regex = Regex("Valve (.+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)")
	return this.split("\n")
		.map { regex.matchEntire(it)!!.groupValues }
		.map { Valve(it.get(1), it.get(2).toInt(), it.get(3).split(", ")) }
		.map { it.label to it }
		.toMap()
}

private data class Valve(
	val label: String,
	val flowRate: Int,
	val leadsTo: List<String>,
	var opened: Boolean = false,
	var distances: Map<String, Int> = emptyMap()
)

private const val test = """Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II"""

private const val input =
	"""Valve QZ has flow rate=0; tunnels lead to valves IR, FA
Valve FV has flow rate=0; tunnels lead to valves AA, GZ
Valve GZ has flow rate=0; tunnels lead to valves FV, PO
Valve QL has flow rate=0; tunnels lead to valves MR, AA
Valve AA has flow rate=0; tunnels lead to valves QL, GQ, EV, FV
Valve SQ has flow rate=23; tunnel leads to valve ZG
Valve PK has flow rate=8; tunnels lead to valves MN, GN, WF, TY, CX
Valve GQ has flow rate=0; tunnels lead to valves AA, MT
Valve TI has flow rate=22; tunnels lead to valves GM, CS
Valve JU has flow rate=17; tunnels lead to valves TT, RR, UJ, JY
Valve YD has flow rate=7; tunnels lead to valves AT, ZS, BS
Valve YB has flow rate=0; tunnels lead to valves EA, MW
Valve FA has flow rate=0; tunnels lead to valves QZ, JT
Valve TN has flow rate=0; tunnels lead to valves ZS, PO
Valve MW has flow rate=0; tunnels lead to valves YB, YL
Valve XN has flow rate=0; tunnels lead to valves VL, VM
Valve MN has flow rate=0; tunnels lead to valves PK, TT
Valve IP has flow rate=9; tunnels lead to valves YC, SA, CH, PI
Valve PD has flow rate=0; tunnels lead to valves YZ, VM
Valve ZS has flow rate=0; tunnels lead to valves TN, YD
Valve PC has flow rate=0; tunnels lead to valves MR, XT
Valve VM has flow rate=13; tunnels lead to valves CX, XN, PD
Valve PO has flow rate=4; tunnels lead to valves GZ, TN, SA, XT, BM
Valve GN has flow rate=0; tunnels lead to valves PK, YL
Valve YL has flow rate=5; tunnels lead to valves MT, YZ, GN, SU, MW
Valve IR has flow rate=6; tunnels lead to valves LK, PI, BM, QZ, EV
Valve GM has flow rate=0; tunnels lead to valves TI, RH
Valve CS has flow rate=0; tunnels lead to valves UJ, TI
Valve EA has flow rate=18; tunnels lead to valves VL, YB, WF, JY
Valve LK has flow rate=0; tunnels lead to valves IR, MR
Valve BM has flow rate=0; tunnels lead to valves IR, PO
Valve JZ has flow rate=0; tunnels lead to valves RH, RR
Valve SA has flow rate=0; tunnels lead to valves IP, PO
Valve XT has flow rate=0; tunnels lead to valves PO, PC
Valve YC has flow rate=0; tunnels lead to valves IP, IL
Valve RH has flow rate=15; tunnels lead to valves WJ, JZ, GM
Valve CH has flow rate=0; tunnels lead to valves IP, BS
Valve JY has flow rate=0; tunnels lead to valves EA, JU
Valve TY has flow rate=0; tunnels lead to valves WJ, PK
Valve WJ has flow rate=0; tunnels lead to valves TY, RH
Valve IL has flow rate=0; tunnels lead to valves YC, MR
Valve BS has flow rate=0; tunnels lead to valves YD, CH
Valve AT has flow rate=0; tunnels lead to valves YD, UX
Valve UJ has flow rate=0; tunnels lead to valves CS, JU
Valve VL has flow rate=0; tunnels lead to valves EA, XN
Valve JT has flow rate=21; tunnels lead to valves ZG, FA
Valve UX has flow rate=10; tunnel leads to valve AT
Valve RR has flow rate=0; tunnels lead to valves JZ, JU
Valve TT has flow rate=0; tunnels lead to valves JU, MN
Valve MT has flow rate=0; tunnels lead to valves GQ, YL
Valve EV has flow rate=0; tunnels lead to valves AA, IR
Valve ZG has flow rate=0; tunnels lead to valves JT, SQ
Valve WF has flow rate=0; tunnels lead to valves EA, PK
Valve YZ has flow rate=0; tunnels lead to valves PD, YL
Valve MR has flow rate=3; tunnels lead to valves LK, IL, QL, SU, PC
Valve PI has flow rate=0; tunnels lead to valves IR, IP
Valve CX has flow rate=0; tunnels lead to valves VM, PK
Valve SU has flow rate=0; tunnels lead to valves YL, MR"""
