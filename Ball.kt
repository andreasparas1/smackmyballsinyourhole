import kotlin.math.*

open class Ball : Entity {
    @JvmField
    var radius: Double

    constructor(m: Double, x: Double, y: Double, r: Double) : super(m, x, y, 0.0, 0.0, 0.0, 0.0, "images/8ball.png") {
        radius = r
    }

    constructor(m: Double, x: Double, y: Double, r: Double, i: String?) : super(m, x, y, 0.0, 0.0, 0.0, 0.0, i) {
        radius = r
    }

    override fun checkCollisions(b: Entity?): Boolean {
        return if (b is Ball) {
            (this.x - b.x) * (this.x - b.x) + (this.y - b.y) * (this.y - b.y) <= (radius + b.radius) * (radius + b.radius)
        } else (b as? Wall)?.checkCollisions(this) //temp statement to keep compiling
            ?: ((b as? Hole)?.checkCollisions(this) ?: false)
    }

    fun unstick(b: Entity) {
        if (b is Ball) {
            if ((this.x - b.x) * (this.x - b.x) + (this.y - b.y) * (this.y - b.y) < (radius + b.radius) * (radius + b.radius)) //balls have clipped into each other
            {
                val change =
                    radius + b.radius - Math.sqrt((this.x - b.x) * (this.x - b.x) + (this.y - b.y) * (this.y - b.y))
                val change1 = change * (mass / (mass + b.mass))
                val change2 = change * (b.mass / (mass + b.mass))
                val w = Math.atan((this.y - b.y) / (this.x - b.x))
                if (this.x > b.x) {
                    this.x = this.x + abs(change1 * cos(w))
                    b.x = b.x - abs(change2 * cos(w))
                } else {
                    this.x = this.x - abs(change1 * cos(w))
                    b.x = b.x + abs(change2 * cos(w))
                }
                if (this.y > b.y) {
                    this.y = this.y + abs(change1 * sin(w))
                    b.y = b.y - abs(change2 * sin(w))
                } else {
                    this.y = this.y - abs(change1 * sin(w))
                    b.y = b.y + abs(change2 * sin(w))
                }
            }

            /*
         double p = this.getXV();
         double h = this.getYV(); 
         double c = b.getXV();
         double j = b.getYV();
         
         
         double a = this.getX();
         double d = b.getX();
         double f = this.getY();
         double g = b.getY();
           
         double k = this.getRadius();
         double l = ((Ball)b).getRadius();
         
         
         double B = (-2*a*c + 2*d*c + 2*a*p - 2*d*p + 2*g*j - 2*g*h + 2*h*f - 2*j*f);
         double A = (c*c + p*p - 2*c*p + j*j + h*h - 2*j*h);
         double C = (a*a - 2*a*d + d*d + g*g + f*f - 2*g*f - k*k - 2*k*l - l*l);
         
         
         double t = (-1*B + Math.sqrt(B*B - 4*A*C)) / 2*A;
         
         
         this.setX(this.getX()+t*this.getXV());
         this.setY(this.getY()+t*this.getYV());
         b.setX(b.getX()+t*b.getXV());
         b.setY(b.getX()+t*b.getYV());
         */
        }
    }

    override fun impact(b: Entity?) {
        if (b is Ball) {
            unstick(b)
            val X1 = this.x
            val Y1 = this.y
            val XV1 = this.xV //values of this ball
            val YV1 = this.yV
            val M1 = mass
            val X2 = b.x
            val Y2 = b.y
            val XV2 = b.xV //values of colliding ball
            val YV2 = b.yV
            val M2 = b.mass
            val w = atan((Y2 - Y1) / (X2 - X1)) //angle between ball centers
            val XV = XV1 - XV2 //values of momentum from perspective of second ball being still
            val YV = YV1 - YV2
            var w2 = w
            if (X2 - X1 < 0) w2 += Math.PI
            val t = atan(YV / XV) //angle of velocity vector
            var t2 = t
            if (XV < 0) t2 += Math.PI
            val impulse = -2 * sqrt(XV * XV + YV * YV) * cos(t2 - w2) / (1 / M1 + 1 / M2)
            addSpeed(impulse / M1, w2)
            b.addSpeed(impulse / M2, w2 + Math.PI)
        } else (b as? Wall)?.impact(this) ?: (b as? Hole)?.impact(this)
    }

    fun impactPoint(X2: Double, Y2: Double) // ball bounces off of a static point
    {
        val XV = this.xV
        val YV = this.yV
        val X1 = this.x
        val Y1 = this.y
        val t = Math.atan(YV / XV) //angle of velocity vector
        var t2 = t
        if (XV < 0) t2 += Math.PI
        val w = Math.atan((Y2 - Y1) / (X2 - X1))
        var w2 = w
        if (X2 - X1 < 0) w2 += Math.PI
        val impulse = 2 * Math.sqrt(XV * XV + YV * YV) * Math.cos(t2 - w2)
        addSpeed(impulse, w2 + Math.PI)
    }

    override fun toString(): String {
        return this.x.toString() + " , " + this.y + " , " + radius
    }
}