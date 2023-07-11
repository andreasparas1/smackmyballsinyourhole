import java.awt.Color

open class Wall(x: Double, y: Double, private val length: Double, val isVert: Boolean, private val color: Color?) :
    Entity(0.0, x, y, 0.0, 0.0, 0.0, 0.0, null) {
    fun length(): Double {
        return length
    }

    fun color(): Color? {
        return color
    }

    fun getLineValues(HEIGHT: Int): IntArray {
        val lineValues = IntArray(4)
        if (isVert) {
            lineValues[0] = x.toInt()
            lineValues[1] = y.toInt()
            lineValues[2] = x.toInt()
            lineValues[3] = (y + length).toInt()
        } else {
            lineValues[0] = x.toInt()
            lineValues[1] = y.toInt()
            lineValues[2] = (x + length).toInt()
            lineValues[3] = y.toInt()
        }
        return lineValues
    }

    override fun checkCollisions(b: Entity?): Boolean {
        if (b is Ball) {
            if (isVert) {
                if (b.x > x && b.x - b.radius <= x && b.y >= y && b.y <= y + length()) //ball hits wall from right
                    return true
                if (b.x < x && b.x + b.radius >= x && b.y >= y && b.y <= y + length()) //ball hits wall from left
                    return true
                if ((b.x - x) * (b.x - x) + (b.y - (y + length)) * (b.y - (y + length)) < b.radius * b.radius) //Ball hits top edge
                    return true
            } else {
                if (b.y < y && b.y + b.radius >= y && b.x >= x && b.x <= x + length()) //ball hits wall from bottom
                    return true
                if (b.y > y && b.y - b.radius <= y && b.x >= x && b.x <= x + length()) //ball hits wall from top
                    return true
                if ((b.x - (x + length)) * (b.x - (x + length)) + (b.y - y) * (b.y - y) < b.radius * b.radius) return true
            }
            if ((b.x - x) * (b.x - x) + (b.y - y) * (b.y - y) < b.radius * b.radius) return true
        } else {
            return false
        }
        return false
    }

    override fun impact(b: Entity?) {
        if (b is Ball) {
            if (isVert) {
                if (b.x > x && b.x - b.radius <= x && b.y >= y && b.y <= y + length()) //ball clips through from right
                {
                    val dx = b.radius - (b.x - x)
                    val dt = dx / b.xV
                    val dy = b.yV * dt
                    b.x = b.x + dx
                    b.y = b.y - dy
                    b.xV = b.xV * -1
                }
                if (b.x < x && b.x + b.radius >= x && b.y >= y && b.y <= y + length()) {
                    val dx = b.radius - (x - b.x)
                    val dt = dx / b.xV
                    val dy = b.yV * dt
                    b.x = b.x - dx
                    b.y = b.y - dy
                    b.xV = b.xV * -1
                }
                if ((b.x - x) * (b.x - x) + (b.y - (y + length)) * (b.y - (y + length)) < b.radius * b.radius) {
                    b.impactPoint(x, y + length)
                }
            } else {
                if (b.y < y && b.y + b.radius >= y && b.x >= x && b.x <= x + length()) //ball hits from bottom
                {
                    val dy = b.radius - (y - b.y)
                    val dt = dy / b.yV
                    val dx = b.xV * dt
                    b.y = b.y - dy
                    b.x = b.x - dx
                    b.yV = b.yV * -1
                }
                if (b.y > y && b.y - b.radius <= y && b.x >= x && b.x <= x + length()) {
                    val dy = b.radius - (b.y - y)
                    val dt = dy / b.yV
                    val dx = b.xV * dt
                    b.y = b.y + dy
                    b.x = b.x - dx
                    b.yV = b.yV * -1
                }
                if ((b.x - (x + length)) * (b.x - (x + length)) + (b.y - y) * (b.y - y) < b.radius * b.radius) {
                    b.impactPoint(x + length, y)
                }
            }
            if ((b.x - x) * (b.x - x) + (b.y - y) * (b.y - y) < b.radius * b.radius) {
                b.impactPoint(x, y)
            }
        }
    }

    override fun toString(): String {
        return "$x , $y , $isVert"
    }
}