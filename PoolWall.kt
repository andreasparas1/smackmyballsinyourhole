import java.awt.Color

class PoolWall(
    x: Double,
    y: Double,
    l: Double,
    isV: Boolean,
    c: Color?, // if true and wall is vertical ball collides from left, if true and wall is horizontal ball collides from bottom
    private val collidingSide: Boolean
) : Wall(x, y, l, isV, c) {
    override fun checkCollisions(b: Entity?): Boolean {
        if (b is Ball) {
            if (this.isVert) {
                if (collidingSide) //ball hits wall from left
                {
                    if (b.x + b.radius >= x && b.y >= y && b.y <= y + length()) return true
                } else  //ball hits wall from right
                {
                    if (b.x - b.radius <= x && b.y >= y && b.y <= y + length()) return true
                }
                if ((b.x - x) * (b.x - x) + (b.y - (y + length())) * (b.y - (y + length())) < b.radius * b.radius) //Ball hits top edge
                    return true
            } else {
                if (collidingSide) //hits from bottom
                {
                    if (b.y + b.radius >= y && b.x >= x && b.x <= x + length()) return true
                } else  //hits from top
                {
                    if (b.y - b.radius <= y && b.x >= x && b.x <= x + length()) return true
                }
                if ((b.x - (x + length())) * (b.x - (x + length())) + (b.y - y) * (b.y - y) < b.radius * b.radius) return true
            }
            if ((b.x - x) * (b.x - x) + (b.y - y) * (b.y - y) < b.radius * b.radius) return true
        } else return false
        return false
    }

    override fun impact(b: Entity?) {
        if (b is Ball) {
            if (this.isVert) {
                if (collidingSide) //ball hits wall from left
                {
                    if (b.x + b.radius >= x && b.y >= y && b.y <= y + length()) {
                        val dx = b.radius - (x - b.x)
                        val dt = dx / b.xV
                        val dy = b.yV * dt
                        b.x = b.x - dx
                        b.y = b.y - dy
                        b.xV = b.xV * -1
                    }
                } else  //ball hits wall from right
                {
                    if (b.x - b.radius <= x && b.y >= y && b.y <= y + length()) {
                        val dx = b.radius - (b.x - x)
                        val dt = dx / b.xV
                        val dy = b.yV * dt
                        b.x = b.x + dx
                        b.y = b.y - dy
                        b.xV = b.xV * -1
                    }
                }
                if ((b.x - x) * (b.x - x) + (b.y - (y + length())) * (b.y - (y + length())) < b.radius * b.radius) //Ball hits top edge
                {
                    b.impactPoint(x, y + length())
                }
            } else {
                if (collidingSide) //hits from bottom
                {
                    if (b.y + b.radius >= y && b.x >= x && b.x <= x + length()) {
                        val dy = b.radius - (y - b.y)
                        val dt = dy / b.yV
                        val dx = b.xV * dt
                        b.x = b.x - dx
                        b.y = b.y - dy
                        b.yV = b.yV * -1
                    }
                } else  //hits from top
                {
                    if (b.y - b.radius <= y && b.x >= x && b.x <= x + length()) {
                        val dy = b.radius - (b.y - y)
                        val dt = dy / b.yV
                        val dx = b.xV * dt
                        b.x = b.x - dx
                        b.y = b.y + dy
                        b.yV = b.yV * -1
                    }
                }
                if ((b.x - (x + length())) * (b.x - (x + length())) + (b.y - y) * (b.y - y) < b.radius * b.radius) {
                    b.impactPoint(x + length(), y)
                }
            }
            if ((b.x - x) * (b.x - x) + (b.y - y) * (b.y - y) < b.radius * b.radius) {
                b.impactPoint(x, y)
            }
        }
    }
}