
import algorithms.Jarvis
import org.junit.Test
import kotlin.test.assertEquals

class JarvisTests {
    @Test(expected = IllegalArgumentException::class)
    fun throwExceptionWhenGivenEmptyList(){
        Jarvis(listOf())
    }

    @Test
    fun generateConvexHullFromSimpleCase(){
        val simplePoints = listOf(Pair(1.0, 2.0), Pair(3.0,3.0), Pair(2.54,3.28),Pair(1.98, 2.22),Pair(0.0, 4.0),Pair(4.0, 4.0),Pair(4.0, 0.0),Pair(0.0, 0.0))
        val convexHull = Jarvis(simplePoints).convexHull()
        val expected = listOf(Pair(4.0, 0.0), Pair(0.0, 0.0), Pair(0.0, 4.0), Pair(4.0, 4.0))
        assertEquals(expected, convexHull)
    }

    @Test
    fun generateConvexHullFromDegenerateCase(){
        val degeneratePoints = listOf(Pair(1.0, 2.0), Pair(3.0,3.0), Pair(2.54,3.28),Pair(1.98, 2.22),Pair(0.0, 4.0),Pair(4.0, 4.0),Pair(4.0, 0.0),Pair(0.0, 0.0), Pair(2.0, 0.0), Pair(0.0, 2.0), Pair(2.0, 4.0), Pair(4.0, 2.0))
        val convexHull = Jarvis(degeneratePoints).convexHull()
        val expected = listOf(Pair(4.0, 0.0), Pair(0.0, 0.0), Pair(0.0, 4.0), Pair(4.0, 4.0))
        assertEquals(expected, convexHull)
    }

    @Test
    fun generateConvexHullFromPoint(){
        val convexHull = Jarvis(listOf(Pair(1.0, 1.0))).convexHull()
        val expected = listOf(Pair(1.0, 1.0))
        assertEquals(expected, convexHull)
    }

    @Test
    fun generateConvexHullFromLine(){
        val convexHull = Jarvis(listOf(Pair(1.0, 1.0), Pair(2.0, 2.0))).convexHull()
        val expected = listOf(Pair(1.0, 1.0), Pair(2.0, 2.0))
        assertEquals(expected, convexHull)
    }

    @Test
    fun generateConvexHullFromLineWithMultiplePoints(){
        val convexHull = Jarvis(listOf(Pair(1.0, 1.0), Pair(2.0, 2.0), Pair(2.5, 2.5), Pair(3.0, 3.0), Pair(4.0, 4.0))).convexHull()
        val expected = listOf(Pair(1.0, 1.0), Pair(4.0, 4.0))
        assertEquals(expected, convexHull)
    }
}