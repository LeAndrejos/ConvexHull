package algorithms

import kotlin.math.sqrt
import kotlin.math.pow

class Jarvis(private val points: List<Pair<Int, Int>>) : ConvexHullAlgorithm{
    private val hullPoints = mutableListOf<Pair<Int, Int>>()
    private var potentialPoints = mutableListOf<Pair<Int, Int>>()

    override fun convexHull(): List<Pair<Int, Int>> {
        val firstPoint = findFirstPoint()
        var hullPoint = firstPoint
        var nextPoint = Pair(0,0)
        do {
            for (p in points) {
                if (p != hullPoint && isHullPointViable(p, hullPoint)){
                    nextPoint = p
                }
            }
            if(potentialPoints.isEmpty()){
                hullPoint = nextPoint
                hullPoints.add(hullPoint)
            }else{
                hullPoint = findFurthestPoint(hullPoint)
                hullPoints.add(hullPoint)
                potentialPoints.clear()
            }
        } while (hullPoint != firstPoint)

        return hullPoints
    }

    private fun findFirstPoint(): Pair<Int, Int> {
        var rightMostElement = points.first().first
        var firstPoint = points.first()
        for (p in points) {
            if (p.first > rightMostElement) {
                firstPoint = p
                rightMostElement = p.first
            }
        }
        return firstPoint
    }

    private fun isHullPointViable(b: Pair<Int, Int>, c: Pair<Int, Int>): Boolean {
        for (a in points) {
            if(a!=b && a!=c && !hullPoints.contains(a)) {
                val p = (c.first - b.first) * (a.second - b.second) - (a.first - b.first) * (c.second - b.second)
                if (p < 0) {
                    return false
                } else if (p == 0) {
                    potentialPoints.add(a)
                }
            }
        }
        return true
    }

    private fun findFurthestPoint(point: Pair<Int, Int>): Pair<Int, Int>{
        var furthestDistance = 0.0
        var furthestPoint = potentialPoints.first()
        for(p in potentialPoints){
            val distance = sqrt(((point.first - p.first).toDouble()).pow(2) + ((point.second - p.second).toDouble()).pow(2))
            if(furthestDistance < distance) {
                furthestDistance = distance
                furthestPoint = p
            }
        }
        return furthestPoint
    }
}