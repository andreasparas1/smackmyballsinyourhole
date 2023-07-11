import javax.swing.ImageIcon

abstract class Entity(//setter methods
    //getter methods
    @JvmField var mass: Double, //pixel positions
    var x: Double, var y: Double, //how many pixels entity moves by each frame;
    var xV: Double, var yV: Double, var xA: Double, var yA: Double, i: String?
) {
    var imageIcon: ImageIcon
        private set

    //constrctor for every variable
    init {
        imageIcon = ImageIcon(i)
    }

    fun setImage(i: String?) {
        imageIcon = ImageIcon(i)
    }

    val xMomentum: Double
        get() = mass * xV
    val yMomentum: Double
        get() = mass * yV

    fun addXMomentum(a: Double) {
        xV += a / mass
    }

    fun addYMomentum(a: Double) {
        yV += a / mass
    }

    fun addSpeed(speed: Double, angle: Double) {
        xV += speed * Math.cos(angle)
        yV += speed * Math.sin(angle)
    }

    //moves entity by its velocity and changes velocity by its acceleration
    fun move() {
        x += xV
        y += yV
    }

    fun Xlerate() {
        xV += xA
    }

    fun Ylerate() {
        yV += yA
    }

    abstract fun checkCollisions(b: Entity?): Boolean
    abstract fun impact(b: Entity?)
}