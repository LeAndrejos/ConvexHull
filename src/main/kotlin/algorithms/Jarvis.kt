package algorithms

import kotlin.math.abs

class Jarvis(private val points: List<Pair<Double, Double>>) : ConvexHullAlgorithm {
    init {
        if (points.isEmpty()) throw IllegalArgumentException("List of points cannot be empty")
    }

    private val hullPoints = mutableListOf<Pair<Double, Double>>()

    override fun convexHull(): List<Pair<Double, Double>> {
        val firstPoint = findFirstPoint()
        var hullPoint = firstPoint
        var nextPoint = firstPoint
        do {
            for (p in points) {
                if (p != hullPoint && isHullPointViable(p, hullPoint)) {
                    nextPoint = p
                }
            }
            hullPoint = nextPoint
            hullPoints.add(hullPoint)
        } while (hullPoint != firstPoint)

        return hullPoints
    }

    private fun findFirstPoint(): Pair<Double, Double> {
        var rightMostElement = points.first().first
        var firstPoint = points.first()
        for (p in points) {
            if (p.first > rightMostElement || (p.first == rightMostElement && p.second > firstPoint.second)) {
                firstPoint = p
                rightMostElement = p.first
            }
        }
        return firstPoint
    }

    private fun isHullPointViable(b: Pair<Double, Double>, c: Pair<Double, Double>): Boolean {
        for (a in points) {
            if (a != b && a != c && !hullPoints.contains(a)) {
                val p = (c.first - b.first) * (a.second - b.second) - (a.first - b.first) * (c.second - b.second)
                if (p < 0) {
                    return false
                } else if (abs(p) == 0.0 && isPointInsideOfSector(a, b, c)) {
                    return false
                }
            }
        }
        return true
    }

    private fun isPointInsideOfSector(
        a: Pair<Double, Double>,
        b: Pair<Double, Double>,
        c: Pair<Double, Double>
    ): Boolean {
        return (b.first >= a.first && b.first <= c.first && b.second >= a.second && b.second <= c.second) || (b.first <= a.first && b.first >= c.first && b.second <= a.second && b.second >= c.second)
    }
}