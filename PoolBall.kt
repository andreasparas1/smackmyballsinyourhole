class PoolBall(x: Double, y: Double, val num: Int) : Ball(20.0, x, y, 10.0, "images/" + num + "ball.png") {

    val isSolid: Boolean
        get() = num < 9

    override fun checkCollisions(b: Entity?): Boolean {
        b?.let {
            return super.checkCollisions(it)
        }
        return false
    }

    override fun impact(b: Entity?) {
        if (b != null) {
            super.impact(b)
        }
    }

}