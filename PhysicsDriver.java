import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.awt.event.*;
public class PhysicsDriver
{

public static PhysicsPanel screen;
public static JFrame frame;
public static int width;
public static int height;

 public static void main(String[]args)throws IOException
   {
      screen = new PhysicsPanel();
      frame = new JFrame("Simple Physics");//window title
      height = 800;
      width = 800;
      frame.setSize(width, height);					//Size of game window
      frame.setLocation(0, 0);					//location of game window on the screen
      frame.setExtendedState(JFrame.NORMAL);  	//MAXIMIZED_BOTH, MAXIMIZED_VERT, or ICONIFIED
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(screen);		
      frame.setVisible(true);
      frame.addKeyListener(new listen());
   }


    public static class listen implements KeyListener 
   {
      public void keyTyped(KeyEvent e)
      {
      

      
      
      }
   
      public void keyPressed(KeyEvent e)
      {
      
      }
   
      public void keyReleased(KeyEvent e)
      {
      
      
      
      }
    }

}