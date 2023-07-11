class Hole(x: Double, y: Double, val radius: Double) : Entity(0.0, x, y, 0.0, 0.0, 0.0, 0.0, "images/8ball.png") {

    override fun checkCollisions(b: Entity?): Boolean {
        return if (b is Ball) {
            (x - b.x) * (x - b.x) + (y - b.y) * (y - b.y) < radius * radius
        } else false
    }

    override fun impact(b: Entity?) {
        if (b is Ball) {
            b.radius = 11.0
        }
    }
}