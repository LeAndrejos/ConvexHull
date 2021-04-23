import algorithms.ConvexHullAlgorithm
import algorithms.Jarvis

fun main(args: Array<String>) {
    val points = listOf(Pair(1,0), Pair(2,1), Pair(3,4), Pair(10,1), Pair(9,4), Pair(7,1), Pair(2,2))
    var a:ConvexHullAlgorithm = Jarvis(points)
    println(a.convexHull())
}