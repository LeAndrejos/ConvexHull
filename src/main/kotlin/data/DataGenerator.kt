package data

import java.util.*
import kotlin.random.Random.Default.nextDouble

class DataGenerator {
    companion object {
        private const val MULTIPLIER = 90.0
        private const val DEGENERATE_POINTS = 2

        fun generateData(numberOfPoints: Int): List<Pair<Double, Double>> {
            if (numberOfPoints < 1) throw IllegalArgumentException("Number of points must be larger than 0")
            val points = mutableListOf<Pair<Double, Double>>()
            val r = Random()

            for (i in 1..numberOfPoints - DEGENERATE_POINTS step 3) {
                generatePointsInLine(points)
            }
            for (i in 0 until ((numberOfPoints - DEGENERATE_POINTS) % 3)) {
                points.add(Pair(r.nextDouble() * MULTIPLIER, r.nextDouble() * MULTIPLIER))
            }
            addDegeneratePoints(points)

            return points
        }

        private fun generatePointsInLine(points: MutableList<Pair<Double, Double>>) {
            val r = Random()

            val x1 = r.nextDouble() * MULTIPLIER
            val y1 = r.nextDouble() * MULTIPLIER
            val x2 = r.nextDouble() * MULTIPLIER
            val y2 = r.nextDouble() * MULTIPLIER

            val a = (y2 - y1) / (x2 - x1)
            val b = y1 - a * x1

            val x3 = if (x2 > x1) nextDouble(x1, x2) else nextDouble(x2, x1)
            val y3 = (a * x3 + b)

            points.addAll(listOf(Pair(x1, y1), Pair(x2, y2), Pair(x3, y3)))
        }

        private fun addDegeneratePoints(points: MutableList<Pair<Double, Double>>) {
            var maxX = 0.0
            var maxY = 0.0
            for (p in points) {
                if (p.first > maxX) maxX = p.first
                if (p.second > maxY) maxY = p.second
            }
            points.addAll(listOf(Pair(maxX, maxY + 10), Pair(maxX, maxY + 20)))
        }
    }
}