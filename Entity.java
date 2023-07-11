import javax.swing.ImageIcon;
public abstract class Entity
{
   private double mass;  
   private double xpos;   //pixel positions
   private double ypos;   
   private double xvel;   //how many pixels entity moves by each frame;
   private double yvel;
   private double xacc;
   private double yacc;

   private ImageIcon image;

//constrctor for every variable
   public Entity(double m, double x, double y, double xv, double yv, double xa, double ya, String i)
   {
      mass = m;
      xpos = x;
      ypos = y;
      xvel = xv;
      yvel = yv;
      xacc = xa;
      yacc = ya;
      image = new ImageIcon(i);
   }

//setter methods
   public void setMass(double m)
   {
      mass = m;
   }

   public void setX(double x)
   {
      xpos = x;
   }

   public void setY(double y)
   {
      ypos = y;
   }

   public void setXV(double x)
   {
      xvel = x;
   }

   public void setYV(double y)
   {
      yvel = y;
   }

   public void setXA(double x)
   {
      xacc = x;
   }

   public void setYA(double y)
   {
      yacc = y;
   }
   public void setImage(String i)
   {
      image = new ImageIcon(i);
   }

//getter methods
   
   public double getMass()
   {
      return mass;
   }
   public double getX()
   {
      return xpos;
   }

   public double getY()
   {
      return ypos;
   }

   public double getXV()
   {
      return xvel;
   }

   public double getYV()
   {
      return yvel;
   }

   public double getXA()
   {
      return xacc;
   }

   public double getYA()
   {
      return yacc;
   }

   public ImageIcon getImageIcon()
   {
      return image;
   }
   
   public double getXMomentum()
   {
      return mass*xvel;
   }
   public double getYMomentum()
   {
      return mass*yvel;
   }
   
   public void addXMomentum(double a)
   {
      xvel+=(a/mass);
   }
   
   public void addYMomentum(double a)
   {
      yvel+=(a/mass);
   }
   
   public void addSpeed(double speed, double angle)
   {
      xvel+=(speed*Math.cos(angle));
      yvel+=(speed*Math.sin(angle));
   
   
   }

//moves entity by its velocity and changes velocity by its acceleration
   public void move()
   {
      xpos+=xvel;
      ypos+=yvel;
   }
   public void Xlerate()
   {
      xvel+=xacc;
   }
   public void Ylerate()
   {
      yvel+=yacc;
   }
   public abstract boolean checkCollisions(Entity b);
   
   public abstract void impact(Entity b);
   
}