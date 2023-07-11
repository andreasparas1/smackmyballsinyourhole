import java.awt.Color
import java.awt.Graphics
import java.awt.event.*
import java.lang.Exception
import javax.swing.ImageIcon
import javax.swing.JPanel
import javax.swing.Timer

class PhysicsPanel : JPanel(), MouseListener, MouseMotionListener {
    init {
        addMouseListener(this)
        addMouseMotionListener(this)
        mouseX = 0
        mouseY = 0
        lineDrawing = 0
        lineValues = IntArray(4)
        lineEntityIndex = -1
        DRAG = 1.0
        Entities = MyArrayList<Entity?>()
        t = Timer(DELAY, Listener())
        t.start()
    }

    fun restartGame() //restarts game when Mode is changed
    {
        frame = 0
        if (MODE == 1) {
            ballPlacing = false
            Entities = MyArrayList<Entity?>()
            WIDTH = 600
            HEIGHT = 600
        }
        if (MODE == 2) //places all entities for pool mode
        {
            DRAG = 0.99
            Entities = MyArrayList<Entity?>()
            WIDTH = 900
            HEIGHT = 500
            addEntity(PoolWall(124.0, 404.0, 307.0, false, Color.black, true))
            addEntity(PoolWall(471.0, 404.0, 302.0, false, Color.black, true))
            addEntity(PoolWall(100.0, 117.0, 267.0, true, Color.black, false))
            addEntity(PoolWall(797.0, 117.0, 267.0, true, Color.black, true))
            addEntity(PoolWall(471.0, 95.0, 302.0, false, Color.black, false))
            addEntity(PoolWall(124.0, 95.0, 307.0, false, Color.black, false))
            addEntity(Hole(95.0, 412.0, 30.0))
            addEntity(Hole(450.0, 428.0, 30.0))
            addEntity(Hole(802.0, 415.0, 30.0))
            addEntity(Hole(802.0, 86.0, 30.0))
            addEntity(Hole(451.0, 70.0, 30.0))
            addEntity(Hole(95.0, 86.0, 30.0))
            addEntity(PoolBall(625.0, 250.0, 0))
            addEntity(PoolBall(280.0, 250.0, 1))
            addEntity(PoolBall(262.0, 261.0, 11))
            addEntity(PoolBall(263.0, 239.0, 5))
            addEntity(PoolBall(244.0, 272.0, 2))
            addEntity(PoolBall(244.0, 250.0, 8))
            addEntity(PoolBall(244.0, 228.0, 10))
            addEntity(PoolBall(226.0, 283.0, 9))
            addEntity(PoolBall(226.0, 261.0, 7))
            addEntity(PoolBall(226.0, 239.0, 14))
            addEntity(PoolBall(226.0, 217.0, 4))
            addEntity(PoolBall(208.0, 294.0, 6))
            addEntity(PoolBall(208.0, 272.0, 15))
            addEntity(PoolBall(208.0, 250.0, 13))
            addEntity(PoolBall(208.0, 228.0, 3))
            addEntity(PoolBall(208.0, 206.0, 12))
        }
    }

    fun printCords() //prints coordinates of all entities
    {
        for (i in 0 until Entities.size()) {
            val x = Entities[i]
        }
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        displayScreen(g)
    }

    fun displayScreen(g: Graphics) //Paints all entities
    {
        if (MODE == 1) {
            g.color = Color.blue
            g.fillRect(0, 0, WIDTH + 200, HEIGHT)
            g.color = Color.black
            g.fillRect(WIDTH, 0, 200, HEIGHT)
            g.drawImage(
                ImageIcon("images/addBallsButton.png").image,
                620,
                50,
                100,
                50,
                null
            ) //buttons for playground mode
            g.drawImage(ImageIcon("images/pGravity.png").image, 620, 120, 100, 50, null)
            g.drawImage(ImageIcon("images/mGravity.png").image, 620, 190, 100, 50, null)
            g.drawImage(ImageIcon("images/pDrag.png").image, 620, 260, 100, 50, null)
            g.drawImage(ImageIcon("images/mDrag.png").image, 620, 330, 100, 50, null)
            g.drawImage(ImageIcon("images/stopButton.png").image, 620, 400, 100, 50, null)
            g.drawImage(ImageIcon("images/mSpeed.png").image, 620, 470, 100, 50, null)
            g.drawImage(ImageIcon("images/pSpeed.png").image, 620, 540, 100, 50, null)
            for (i in 0 until Entities.size()) {
                paintEntity(g, Entities[i])
            }
            if (lineDrawing.toInt() == 1) {
                drawLine(g, lineValues, Color.red)
            }
        } else if (MODE == 0) //title screen mode
        {
            g.color = Color.blue
            g.fillRect(0, 0, 800, 800)
            g.drawImage(ImageIcon("images/titleblock.png").image, 100, 100, 300, 200, null)
            g.drawImage(ImageIcon("images/Mode1Button.png").image, 21, 328, 98, 65, null)
            g.drawImage(ImageIcon("images/Mode2Button.png").image, 140, 328, 98, 65, null)
        } else if (MODE == 2) //pool game mode
        {
            g.color = Color.black
            g.fillRect(0, 0, 1000, 500)
            g.drawImage(ImageIcon("images/scratchButton.png").image, 700, 0, 100, 50, null)
            g.drawImage(ImageIcon("images/pooltable.png").image, 50, 50, 800, 400, null)
            for (i in 0 until Entities.size()) {
                paintEntity(g, Entities[i])
            }
            if (lineDrawing.toInt() == 1) {
                drawLine(g, lineValues, Color.red)
                if (MODE == 2) {
                    val linVs = IntArray(4)
                    linVs[0] = lineValues[0]
                    linVs[1] = lineValues[1]
                    linVs[2] = lineValues[0] + (lineValues[0] - lineValues[2])
                    linVs[3] = lineValues[1] + (lineValues[1] - lineValues[3])
                    drawLine(g, linVs, Color.white)
                }
            }
        }
    }

    fun paintEntity(g: Graphics, x: Entity?) //Paints specific entity
    {
        if (x is Ball) {
            val radius = x.radius.toInt()
            val xpos = (x.x - radius).toInt()
            val ypos = (x.y + radius).toInt()
            g.drawImage(x.imageIcon.image, xpos, HEIGHT - ypos, radius * 2, radius * 2, null)
        } else if (x is Wall) {
            drawLine(g, x.getLineValues(HEIGHT), Color.black)
        }
        /*else if(x instanceof Hole) //testing for seeing location of holes
      {
      int radius = (int)(((Hole)(x)).getRadius());
         int xpos = (int)(x.getX()-radius);
         int ypos = (int)(x.getY()+radius);
         
         g.drawImage(x.getImageIcon().getImage(), xpos, HEIGHT - ypos, radius*2, radius*2, null);
      }*/
    }

    fun totalKE(): Double //total kinetic energy of the system
    {
        var ans = 0.0
        for (i in 0 until Entities.size()) {
            val j = Entities[i]
            val vs = j!!.xV * j.xV + j.yV * j.yV
            ans += 0.5 * j.mass * vs
        }
        return ans
    }

    fun addEntity(x: Entity?) //adds entity to the panel
    {
        Entities.add(x)
    }

    fun update() //called for each timer iteration moving all entities and checks for collisions and resolves them
    {
        try {
            var index = 1
            for (k in 0 until Entities.size()) {
                val x = Entities[k]
                if (x is Ball && x.radius == 11.0) {
                    Entities.remove(k)
                    if (MODE == 2 && x is PoolBall && x.num == 0) whiteBallIn = true
                }
                x!!.move()
                if (x is Ball) {
                    if (x.x + x.radius >= WIDTH || x.x - x.radius <= 0) {
                        if (x.x - x.radius < 0) {
                            val dx = x.radius - x.x
                            val dt = dx / x.xV
                            val dy = x.yV * dt
                            x.x = x.x + dx
                            x.y = x.y - dy
                        }
                        if (x.x + x.radius > WIDTH) {
                            val dx = x.radius - (WIDTH - x.x)
                            val dt = dx / x.xV
                            val dy = x.yV * dt
                            x.x = x.x - dx
                            x.y = x.y - dy
                        }
                        x.xV = x.xV * -1
                    } else x.Xlerate()
                    if (x.y + x.radius >= HEIGHT || x.y - x.radius <= 0) {
                        if (x.y - x.radius < 0) {
                            val dy = x.radius - x.y
                            val dt = dy / x.yV
                            val dx = x.xV * dt
                            x.x = x.x - dx
                            x.y = x.y + dy
                        }
                        if (x.y + x.radius > HEIGHT) {
                            val dy = x.radius - (HEIGHT - x.y)
                            val dt = dy / x.yV
                            val dx = x.xV * dt
                            x.x = x.x - dx
                            x.y = x.y - dy
                        }
                        x.yV = x.yV * -1
                    } else x.Ylerate()
                }
                for (i in index until Entities.size()) {
                    if (x.checkCollisions(Entities[i])) {
                        x.impact(Entities[i])
                        collisionCount++
                    }
                }
                index++
                x.xV = x.xV * DRAG
                x.yV = x.yV * DRAG
            }
            if (lineDrawing.toInt() == 1) // sets points of where the line to add force is drawn
            {
                lineValues[0] = Entities[lineEntityIndex]!!.x.toInt()
                lineValues[1] = Entities[lineEntityIndex]!!.y.toInt()
                lineValues[2] = mouseX
                lineValues[3] = mouseY
            }
        }
        catch (_: Exception) {

        }

    }

    fun drawLine(g: Graphics, values: IntArray, color: Color?) // draws a line with an int array of coordinates
    {
        g.color = color
        g.drawLine(values[0], HEIGHT - values[1], values[2], HEIGHT - values[3])
    }

    /*
   public int getHeight()
   {
      return HEIGHT;
   }
   public int getWidth()
   {
      return WIDTH;
   }
   */
    private inner class Listener : ActionListener {
        override fun actionPerformed(e: ActionEvent) //this is called for each timer iteration
        {
            if (MODE != 0) {
                frame++
                if (frame == Int.MAX_VALUE) frame = 0
                if (frame % speed == 0) {
                    update()
                }
            }
            repaint()
        }
    }

    override fun mouseClicked(e: MouseEvent) {
        if (MODE == 0) {
            mouseX = e.x
            mouseY = e.y
            if (mouseX >= 21 && mouseX <= 119 && mouseY >= 328 && mouseY <= 393) {
                MODE = 1
                restartGame()
            }
            if (mouseX >= 140 && mouseX <= 238 && mouseY >= 328 && mouseY <= 393) {
                MODE = 2
                restartGame()
            }
        }
        if (MODE == 2) {
            mouseX = e.x
            mouseY = e.y
            if (whiteBallIn) {
                addEntity(PoolBall(mouseX.toDouble(), (HEIGHT - mouseY).toDouble(), 0))
                val x = Entities[Entities.size() - 1] as PoolBall?
                whiteBallIn = false
                for (i in 0 until Entities.size() - 1) {
                    if (x!!.checkCollisions(Entities[i]!!)) {
                        Entities.remove(Entities.size() - 1)
                        whiteBallIn = true
                    }
                }
            }
            if (mouseX >= 700 && mouseX <= 800 && mouseY >= 0 && mouseY <= 50) {
                for (i in 0 until Entities.size()) {
                    if (Entities[i] is PoolBall && (Entities[i] as PoolBall?)!!.num == 0) {
                        Entities.remove(i)
                        whiteBallIn = true
                    }
                }
            }
        }
        if (MODE == 1) {
            mouseX = e.x
            mouseY = e.y
            if (mouseX >= 620 && mouseX <= 720 && mouseY >= 50 && mouseY <= 100) {
                ballPlacing = !ballPlacing
            } else if (ballPlacing) {
                addEntity(Ball(10.0, mouseX.toDouble(), (HEIGHT - mouseY).toDouble(), 30.0))
            } else if (mouseX >= 620 && mouseX <= 720 && mouseY >= 120 && mouseY <= 170) {
                for (i in 0 until Entities.size()) {
                    val x = Entities[i]
                    if (x is Ball) x.yA = x.yA - 0.2
                }
            } else if (mouseX >= 620 && mouseX <= 720 && mouseY >= 190 && mouseY <= 240) {
                for (i in 0 until Entities.size()) {
                    val x = Entities[i]
                    if (x is Ball) x.yA = x.yA + 0.2
                }
            } else if (mouseX >= 620 && mouseX <= 720 && mouseY >= 260 && mouseY <= 310) {
                DRAG -= 0.01
            } else if (mouseX >= 620 && mouseX <= 720 && mouseY >= 330 && mouseY <= 380) {
                if (DRAG <= 0.99) DRAG += 0.01
            } else if (mouseX >= 620 && mouseX <= 720 && mouseY >= 400 && mouseY <= 450) {
                for (i in 0 until Entities.size()) {
                    val x = Entities[i]
                    x!!.xV = 0.0
                    x.yV = 0.0
                    x.xA = 0.0
                    x.yA = 0.0
                }
            } else if (mouseX >= 620 && mouseX <= 720 && mouseY >= 470 && mouseY <= 520) {
                speed++
            } else if (mouseX >= 620 && mouseX <= 720 && mouseY >= 540 && mouseY <= 590) {
                if (speed > 1) speed--
            }
        }
    }

    override fun mousePressed(e: MouseEvent) {
        if (MODE == 1) {
            mouseX = e.x
            mouseY = HEIGHT - e.y
            for (i in 0 until Entities.size()) {
                if (Entities[i] is Ball) {
                    val x = Entities[i]?.x?.toInt()
                    val y = Entities[i]?.y?.toInt()
                    if ((mouseX - x!!) * (mouseX - x) + (mouseY - y!!) * (mouseY - y) < (Entities[i] as Ball?)!!.radius * (Entities[i] as Ball?)!!.radius) {
                        lineEntityIndex = i
                        lineDrawing = 1
                        break
                    }
                }
            }
        }
        if (MODE == 2) {
            mouseX = e.x
            mouseY = HEIGHT - e.y
            if (totalKE() <= 0.1) {
                for (i in 0 until Entities.size()) {
                    if (Entities[i] is PoolBall) {
                        val x = Entities[i]?.x?.toInt()
                        val y = Entities[i]?.y?.toInt()
                        if ((mouseX - x!!) * (mouseX - x) + (mouseY - y!!) * (mouseY - y) < (Entities[i] as Ball?)!!.radius * (Entities[i] as Ball?)!!.radius && (Entities[i] as PoolBall?)!!.num == 0) {
                            lineEntityIndex = i
                            lineDrawing = 1
                            break
                        }
                    }
                }
            }
        }
    }

    override fun mouseReleased(e: MouseEvent) {
        if (MODE == 1 || MODE == 2) {
            if (lineDrawing.toInt() == 1) {
                lineDrawing = 0
                val xspeed = (lineValues[0] - lineValues[2]).toDouble()
                val yspeed = (lineValues[1] - lineValues[3]).toDouble()
                val x = Entities[lineEntityIndex]
                x!!.xV = x.xV + (xspeed / x.mass)
                x.yV = x.yV + (yspeed / x.mass)
            }
        }
    }

    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseMoved(e: MouseEvent) {}
    override fun mouseDragged(e: MouseEvent) {
        if (lineDrawing.toInt() == 1) {
            mouseX = e.x
            mouseY = HEIGHT - e.y
            lineValues[2] = mouseX
            lineValues[3] = mouseY
        }
    }

    override fun mouseExited(e: MouseEvent) {}

    companion object {
        private var collisionCount = 0
        protected var HEIGHT = 600
        protected var WIDTH = 600
        protected const val textSize = 25
        protected var DELAY = 1
        protected lateinit var t: Timer
        protected lateinit var keys: BooleanArray
        protected var MODE = 0
        protected var mouseX: Int = 0
        protected var mouseY: Int = 0
        protected var frame //int tracking frame number
                = 0
        protected var speed //number of frames which will go by before positions are updated: lower is faster
                = 1
        protected lateinit var Entities: MyArrayList<Entity?>
        protected var lineDrawing //value keeping track of if line 
                : Byte = 0
        protected lateinit var lineValues //5 int array tracking start and end coordinates
                : IntArray
        protected var lineEntityIndex //index of where line is being drawn from
                : Int = 0
        protected var DRAG // magnitude of change in velocity of all moving entities
                : Double = 0.0
        protected var whiteBallIn = false //keeps track of if white ball needs to be placed in pool mode
        protected var ballPlacing = false //keeps track of ball placing in playground
    }
}
