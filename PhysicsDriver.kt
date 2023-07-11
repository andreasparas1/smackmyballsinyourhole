import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

object PhysicsDriver {
    var screen: PhysicsPanel? = null
    var frame: JFrame? = null
    var width = 0
    var height = 0
    @JvmStatic
    fun main(args: Array<String>) {
        screen = PhysicsPanel()
        frame = JFrame("Simple Physics") //window title
        height = 800
        width = 800
        frame!!.setSize(width, height) //Size of game window
        frame!!.setLocation(0, 0) //location of game window on the screen
        frame!!.extendedState = JFrame.NORMAL //MAXIMIZED_BOTH, MAXIMIZED_VERT, or ICONIFIED
        frame!!.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame!!.contentPane = screen
        frame!!.isVisible = true
        frame!!.addKeyListener(listen())
    }

    class listen : KeyListener {
        override fun keyTyped(e: KeyEvent) {}
        override fun keyPressed(e: KeyEvent) {}
        override fun keyReleased(e: KeyEvent) {}
    }
}