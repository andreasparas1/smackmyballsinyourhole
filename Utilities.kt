import kotlin.math.sqrt

object Utilities {
    fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val a = Ball(10.0, 14.0, 28.0, 10.0)
        val b = Ball(10.0, 19.0, 9.0, 10.0)
        b.yV = 1.0
        println(sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)))
        a.unstick(b)
        println(sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)))
    }
}