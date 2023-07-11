import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
public class PhysicsPanel extends JPanel implements MouseListener, MouseMotionListener
{
   private static int collisionCount = 0;
   
   
   protected static int HEIGHT;
   protected static int WIDTH;
   protected static final int textSize = 25;
   protected static int DELAY;
   protected static Timer t;
   protected static boolean [] keys;
   protected static int MODE;
   protected static int mouseX;						
   protected static int mouseY;
   protected static int frame;  //int tracking frame number
   protected static int speed;  //number of frames which will go by before positions are updated: lower is faster

   protected static MyArrayList<Entity> Entities;
   
   protected static byte lineDrawing;  //value keeping track of if line 
   protected static int[] lineValues; //5 int array tracking start and end coordinates
   protected static int lineEntityIndex; //index of where line is being drawn from
   
   protected static double DRAG; // magnitude of change in velocity of all moving entities
   
   protected static boolean whiteBallIn; //keeps track of if white ball needs to be placed in pool mode
   
   protected static boolean ballPlacing; //keeps track of ball placing in playground

   

   public PhysicsPanel()
   {
      HEIGHT = 600;
      WIDTH = 600;
      frame = 0;
      speed = 1;
      DELAY = 1;
      MODE = 0;
      addMouseListener(this);
      addMouseMotionListener( this );
     
   
      mouseX = 0; 
      mouseY = 0;
      
      lineDrawing = 0;
      lineValues = new int[4];
      lineEntityIndex = -1;
      
      DRAG = 1;
   
      Entities = new MyArrayList();
      t = new Timer(DELAY, new Listener());
      t.start();
   }
   
   public void restartGame()  //restarts game when Mode is changed
   {
      frame = 0;
      if(MODE == 1)
      {
         ballPlacing = false;
         Entities = new MyArrayList();
         WIDTH = 600;
         HEIGHT = 600;
         
      }
      if(MODE == 2)//places all entities for pool mode
      {
         DRAG = 0.99;
         Entities = new MyArrayList();
         WIDTH = 900;
         HEIGHT = 500;
         
         addEntity(new PoolWall(124, 404, 307, false, Color.black, true));
         addEntity(new PoolWall(471, 404, 302, false, Color.black, true));
         addEntity(new PoolWall(100, 117, 267, true, Color.black, false));
         addEntity(new PoolWall(797, 117, 267, true, Color.black, true));
         addEntity(new PoolWall(471, 95, 302, false, Color.black, false));
         addEntity(new PoolWall(124, 95, 307, false, Color.black, false));
         
         addEntity(new Hole(95, 412, 30));
         addEntity(new Hole(450 , 428, 30));
         addEntity(new Hole(802 , 415, 30));
         addEntity(new Hole(802 , 86, 30));
         addEntity(new Hole(451 , 70, 30));
         addEntity(new Hole(95 , 86, 30));
         
         addEntity(new PoolBall(625, 250, 0));
         addEntity(new PoolBall(280, 250, 1));
         addEntity(new PoolBall(262, 261, 11));
         addEntity(new PoolBall(263, 239, 5));
         addEntity(new PoolBall(244, 272, 2));
         addEntity(new PoolBall(244, 250, 8));
         addEntity(new PoolBall(244, 228, 10));
         addEntity(new PoolBall(226, 283, 9));
         addEntity(new PoolBall(226, 261, 7));
         addEntity(new PoolBall(226, 239, 14));
         addEntity(new PoolBall(226, 217, 4));
         addEntity(new PoolBall(208, 294, 6));
         addEntity(new PoolBall(208, 272, 15));
         addEntity(new PoolBall(208, 250, 13));
         addEntity(new PoolBall(208, 228, 3));
         addEntity(new PoolBall(208, 206, 12));
      
      
         
      
      }
   }
   
   public void printCords() //prints cordintes of all entities
   {
      for(int i = 0; i<Entities.size(); i++)
      {
         Entity x = Entities.get(i);
         
      }
   
   }
   
   

   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      
      displayScreen(g);
   }
   
   public void displayScreen(Graphics g)  //Paints all entities
   {
      if(MODE == 1)
      {
         
         g.setColor(Color.blue);
         g.fillRect(0, 0, WIDTH+200, HEIGHT); 
         g.setColor(Color.black);
         g.fillRect(WIDTH, 0, 200, HEIGHT);
        
         g.drawImage((new ImageIcon("images/addBallsButton.png")).getImage(), 620, 50, 100, 50, null); //buttons for playground mode
         g.drawImage((new ImageIcon("images/pGravity.png")).getImage(), 620, 120, 100, 50, null);
         g.drawImage((new ImageIcon("images/mGravity.png")).getImage(), 620, 190, 100, 50, null);
         g.drawImage((new ImageIcon("images/pDrag.png")).getImage(), 620, 260, 100, 50, null);
         g.drawImage((new ImageIcon("images/mDrag.png")).getImage(), 620, 330, 100, 50, null);
         g.drawImage((new ImageIcon("images/stopButton.png")).getImage(), 620, 400, 100, 50, null);
         g.drawImage((new ImageIcon("images/mSpeed.png")).getImage(), 620, 470, 100, 50, null);
          g.drawImage((new ImageIcon("images/pSpeed.png")).getImage(), 620, 540, 100, 50, null);
         for(int i = 0; i<Entities.size(); i++)
         {
            paintEntity(g, Entities.get(i));
         }
         
         if(lineDrawing == 1)
         {
            drawLine(g, lineValues, Color.red);
         } 
      }
      else if(MODE == 0) //title screen mode
      {
         g.setColor(Color.blue);
         g.fillRect(0, 0, 800, 800);
         g.drawImage((new ImageIcon("images/titleblock.png")).getImage(), 100, 100, 300, 200, null);
         g.drawImage((new ImageIcon("images/Mode1Button.png")).getImage(), 21, 328, 98, 65, null);
         g.drawImage((new ImageIcon("images/Mode2Button.png")).getImage(), 140, 328, 98, 65, null);
      }
      else if(MODE == 2)//pool game mode
      {
         g.setColor(Color.black);
         g.fillRect(0, 0, 1000, 500);
         g.drawImage((new ImageIcon("images/scratchButton.png")).getImage(), 700, 0, 100, 50, null);
         g.drawImage((new ImageIcon("images/pooltable.png")).getImage(), 50, 50, 800, 400, null);
         for(int i = 0; i<Entities.size(); i++)
         {
            paintEntity(g, Entities.get(i));
         }
         
         if(lineDrawing == 1)
         {
            drawLine(g, lineValues, Color.red);
            if(MODE == 2)
            {
               int[] linVs = new int[4];
               linVs[0] = lineValues[0];
               linVs[1] = lineValues[1];
               linVs[2] = lineValues[0] + (lineValues[0] - lineValues[2]);
               linVs[3] = lineValues[1] + (lineValues[1]- lineValues[3]);
               drawLine(g, linVs, Color.white);
            
            }
         }
      }
   
   }
   public void paintEntity(Graphics g, Entity x)  //Paints specific entity
   {
      if(x instanceof Ball)
      {
         int radius = (int)(((Ball)(x)).getRadius());
         int xpos = (int)(x.getX()-radius);
         int ypos = (int)(x.getY()+radius);
         
         g.drawImage(x.getImageIcon().getImage(), xpos, HEIGHT - ypos, radius*2, radius*2, null);
      }
      else if(x instanceof Wall)
      {
         drawLine(g, ((Wall)x).getLineValues(HEIGHT), Color.black);
      }
      /*else if(x instanceof Hole) //testing for seeing location of holes
      {
      int radius = (int)(((Hole)(x)).getRadius());
         int xpos = (int)(x.getX()-radius);
         int ypos = (int)(x.getY()+radius);
         
         g.drawImage(x.getImageIcon().getImage(), xpos, HEIGHT - ypos, radius*2, radius*2, null);
      }*/
   }
   
   
   public double totalKE() //total kinetic energy of the system
   {
      double ans = 0;
      for(int i = 0; i<Entities.size(); i++)
      {
         Entity j = Entities.get(i);
      
         double vs = j.getXV()*j.getXV()+j.getYV()*j.getYV();
         ans += 0.5 * j.getMass() * vs;
      }
      return ans;
   }
   
   public void addEntity(Entity x) //adds entity to the panel
   {
      Entities.add(x);
   }
   
   public void update() //called for each timer iteration moving all entities and checks for collisions and resolves them
   {  
      
      int index = 1;
      for(int k = 0; k<Entities.size(); k++)
      {
         Entity x = Entities.get(k);
         if(x instanceof Ball && ((Ball)x).getRadius() == 11)
         {
            Entities.remove(k);
            if(MODE == 2 && x instanceof PoolBall && ((PoolBall)x).getNum() == 0)
               whiteBallIn = true;
         
         }
         x.move();
         if(x instanceof Ball)
         {
            if(x.getX()+((Ball)(x)).getRadius() >= WIDTH || x.getX()-((Ball)(x)).getRadius() <= 0)
            {
               if(x.getX()-((Ball)(x)).getRadius() < 0)
               {
                  double dx = ((Ball)x).getRadius() - x.getX();
                  double dt = dx/(x.getXV());
                  double dy = x.getYV() * dt;
                  
                  x.setX(x.getX() + dx);
                  x.setY(x.getY() - dy);
               }
               if(x.getX()+((Ball)(x)).getRadius() > WIDTH)
               {
                  double dx = ((Ball)x).getRadius() - (WIDTH - x.getX());
                  double dt = dx/(x.getXV());
                  double dy = x.getYV() * dt;
                  
                  x.setX(x.getX() - dx); 
                  x.setY(x.getY() - dy);
               }
               
               
               x.setXV(x.getXV()*-1);
            }
            else
               x.Xlerate();
            if(x.getY()+((Ball)(x)).getRadius() >= HEIGHT || x.getY()-((Ball)(x)).getRadius() <= 0)
            {
               if(x.getY()-((Ball)(x)).getRadius() < 0)
               {
                  double dy = ((Ball)x).getRadius() - x.getY();
                  double dt = dy/(x.getYV());
                  double dx = x.getXV() * dt;
                  x.setX(x.getX() - dx);
                  x.setY(x.getY() + dy);
               } 
               if(x.getY()+((Ball)(x)).getRadius() > HEIGHT)
               {
                  double dy = ((Ball)x).getRadius() - (HEIGHT - x.getY());
                  double dt = dy/(x.getYV());
                  double dx = x.getXV() * dt;
                  x.setX(x.getX() - dx);
                  x.setY(x.getY() - dy);
               }
               x.setYV(x.getYV()*-1); 
            }
            else
               x.Ylerate();
         }
         for(int i = index; i<Entities.size(); i++)
         {
            if(x.checkCollisions(Entities.get(i)))
            {
               x.impact(Entities.get(i));
               
               collisionCount++;
            }
         }
         index++;
         
         x.setXV(x.getXV()*DRAG);
         x.setYV(x.getYV()*DRAG);
         
      }
      
      if(lineDrawing == 1) // sets points of where the line to add force is drawn
      {
         lineValues[0] = (int)Entities.get(lineEntityIndex).getX();
         lineValues[1] = (int)Entities.get(lineEntityIndex).getY();
         lineValues[2] = mouseX;
         lineValues[3] = mouseY;
      }
       
   }
   
   public void drawLine(Graphics g, int[] values, Color color)// draws a line with an int array of coordinates
   {
      g.setColor(color);
      g.drawLine(values[0], HEIGHT - values[1], values[2], HEIGHT - values[3]);   
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
   
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)	//this is called for each timer iteration
      {
         if(MODE != 0)
         {
            frame++;
            if(frame == Integer.MAX_VALUE)
               frame = 0;
         
            if(frame % speed == 0)
            {
               update();
            }
            
               
         }
         
         repaint(); 
      }
   }
   
   public void mouseClicked( MouseEvent e )
   {
      if(MODE == 0)
      {
         mouseX = e.getX();
         mouseY = e.getY();
         
         
         
         if(mouseX>=21 && mouseX<=119 && mouseY>=328 && mouseY<=393)
         {
            MODE = 1;
            restartGame();
            
         }
         if(mouseX>=140 && mouseX<=238 && mouseY>=328 && mouseY<=393)
         {
            MODE = 2;
            restartGame();
         }
      }
      if(MODE == 2)
      {
         mouseX = e.getX();
         mouseY = e.getY();
         
         if(whiteBallIn)
         {
            addEntity(new PoolBall(mouseX, HEIGHT - mouseY, 0));
            PoolBall x = (PoolBall)Entities.get(Entities.size() - 1);
         
            whiteBallIn = false;
            for(int i = 0; i<Entities.size()-1; i++)
            {
               if(x.checkCollisions(Entities.get(i)))
               {
                  Entities.remove(Entities.size()-1);
                  whiteBallIn = true;
               }
            }
         }
         if(mouseX >= 700 && mouseX <=800 && mouseY >=0 && mouseY <=50)
         {
            for(int i = 0; i<Entities.size(); i++)
            {
               if(Entities.get(i) instanceof PoolBall && ((PoolBall)Entities.get(i)).getNum() == 0)
               {
                  Entities.remove(i);
                  whiteBallIn = true;
               }
            }
         }
         
      }
      if(MODE == 1)
      {
         mouseX = e.getX();
         mouseY = e.getY();
         
         
         if(mouseX >= 620 && mouseX <=720 &&mouseY >= 50 && mouseY<=100)
         {
            
            ballPlacing = !ballPlacing;
         }
         else if(ballPlacing)
         {
            addEntity(new Ball(10, mouseX, HEIGHT - mouseY, 30));
         }
         else if(mouseX >= 620 && mouseX <=720 && mouseY >=120 && mouseY <=170)
         {
            for(int i = 0; i<Entities.size(); i++)
            {
               Entity x = Entities.get(i);
               if(x instanceof Ball)
                  x.setYA(x.getYA() - 0.2);
            }
         }
         else if(mouseX >= 620 && mouseX <=720 && mouseY >=190 && mouseY <=240)
         {
            for(int i = 0; i<Entities.size(); i++)
            {
               Entity x = Entities.get(i);
               if(x instanceof Ball)
                  x.setYA(x.getYA() + 0.2);
            }
         }
         else if(mouseX >= 620 && mouseX <=720 && mouseY >=260 && mouseY <=310)
         {
            DRAG -= 0.01;
         }
         else if(mouseX >= 620 && mouseX <=720 && mouseY >=330 && mouseY <=380)
         {
            if(DRAG <=0.99)
               DRAG += 0.01;
         }
         else if(mouseX >= 620 && mouseX <=720 && mouseY >=400 && mouseY <=450)
         {
            for(int i = 0; i<Entities.size(); i++)
            {
               Entity x = Entities.get(i);
               x.setXV(0);
               x.setYV(0);
               x.setXA(0);
               x.setYA(0);
            }
         }
         else if(mouseX >= 620 && mouseX <=720 && mouseY >=470 && mouseY <=520)
         {
         speed++;
         }
         else if(mouseX >= 620 && mouseX <=720 && mouseY >=540 && mouseY <=590)
         {
         if(speed>1)
         speed--;
         }
         
         
      }
   }
   
   public void mousePressed( MouseEvent e )
   {
      if(MODE == 1)
      {
         mouseX = e.getX();
         mouseY = HEIGHT - e.getY();
         
         
         for(int i = 0; i<Entities.size(); i++)
         {
            if(Entities.get(i) instanceof Ball)
            {
               int x = (int)Entities.get(i).getX();
               int y = (int)Entities.get(i).getY();
               if((mouseX-x)*(mouseX-x)+(mouseY-y)*(mouseY-y) < ((Ball)Entities.get(i)).getRadius() * ((Ball)Entities.get(i)).getRadius())
               {
                  
                  lineEntityIndex = i;
                  lineDrawing = 1;
                  break;
               
               }
            }
         }
         
        
      }
      if(MODE == 2)
      {
         mouseX = e.getX();
         mouseY = HEIGHT - e.getY();
         
         if(totalKE() <= 0.1)
         {
            for(int i = 0; i<Entities.size(); i++)
            {
               if(Entities.get(i) instanceof PoolBall)
               {
                  int x = (int)Entities.get(i).getX();
                  int y = (int)Entities.get(i).getY();
                  if((mouseX-x)*(mouseX-x)+(mouseY-y)*(mouseY-y) < ((Ball)Entities.get(i)).getRadius() * ((Ball)Entities.get(i)).getRadius() && ((PoolBall)Entities.get(i)).getNum()==0 )
                  {
                  
                     lineEntityIndex = i;
                     lineDrawing = 1;
                     break;
                  
                  }
               }
            }
         }
      }
   
   }
   public void mouseReleased( MouseEvent e )
   {
      if(MODE == 1 || MODE == 2)
      {
         if(lineDrawing == 1)
         {
            lineDrawing = 0;
            
            double xspeed = (double)(lineValues[0]-lineValues[2]);
            double yspeed = (double)(lineValues[1]-lineValues[3]);
            
            Entity x = Entities.get(lineEntityIndex);
            x.setXV(x.getXV() + (xspeed/x.getMass()));
            x.setYV(x.getYV() + (yspeed/x.getMass()));
           
            
         }
      
      }
   
   }

   public void mouseEntered( MouseEvent e )
   {}

   public void mouseMoved( MouseEvent e)
   {}
   public void mouseDragged( MouseEvent e)
   {
      if(lineDrawing == 1)
      {
         mouseX = e.getX();
         mouseY = HEIGHT - e.getY();
      
         lineValues[2] = mouseX;
         lineValues[3] = mouseY;
      }
   }

   public void mouseExited( MouseEvent e )
   {}
}
