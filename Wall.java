import java.awt.*;
public class Wall extends Entity
{
   private double length;
   private boolean isVertical;
   private Color color;

   public Wall(double x, double y, double l, boolean isV, Color c)
   {
      super(0, x, y, 0, 0, 0, 0, null);
      length = l;
      isVertical = isV;
      color = c;
   }

   public double length()
   {
      return length;
   }
   public boolean isVert()
   {
      return isVertical;
   }
   public Color color()
   {
      return color;
   }
   public int[] getLineValues(int HEIGHT)
   {
      int[] lineValues = new int[4];
      if(isVertical)
      {
         lineValues[0] = (int)this.getX();
         lineValues[1] = (int)(this.getY());
         lineValues[2] = (int)this.getX();
         lineValues[3] = (int)(this.getY() + length);
      }
      else
      {
         lineValues[0] = (int)this.getX();
         lineValues[1] = (int)(this.getY());
         lineValues[2] = (int)(this.getX() + length);
         lineValues[3] = (int)(this.getY());
      }
   
      return lineValues;
   }
   
   public boolean checkCollisions(Entity b)
   {
      if(b instanceof Ball)
      {
         if(isVertical)
         {
            if(b.getX()>this.getX() && b.getX() - ((Ball)b).getRadius() <= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length()) //ball hits wall from right
               return true;
            if(b.getX()<this.getX() && b.getX() + ((Ball)b).getRadius() >= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length()) //ball hits wall from left
               return true;
            if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - (this.getY() + length)) * (b.getY() - (this.getY()+length)) < ((Ball)b).getRadius() * ((Ball)b).getRadius())  //Ball hits top edge
            return true;
            
         }
         else
         {
            if(b.getY() < this.getY() &&  b.getY() + ((Ball)b).getRadius() >= this.getY() &&  b.getX() >= this.getX() && b.getX() <= this.getX() + this.length())  //ball hits wall from bottom
               return true;
            if(b.getY() > this.getY() &&  b.getY() - ((Ball)b).getRadius() <= this.getY() &&  b.getX() >= this.getX() && b.getX() <= this.getX() + this.length()) //ball hits wall from top
               return true;
            if((b.getX()-(this.getX()+length)) * (b.getX()-(this.getX()+length)) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
            return true;
         }
         if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
            return true;
      }
      else
      {
         return false;
      }
      return false;
   }
   
   public void impact(Entity b)
   {
      if(b instanceof Ball)
      {
         if(isVertical)
         {
            if(b.getX() > this.getX() && b.getX() - ((Ball)b).getRadius() <= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length()) //ball clips through from right
            {
            double dx = ((Ball)b).getRadius() - (b.getX() - this.getX());
            double dt = dx/(b.getXV());
            double dy = b.getYV() * dt;
            
            b.setX(b.getX() + dx);
            b.setY(b.getY() - dy);
            
            
            b.setXV(b.getXV() * -1);
            }
            if(b.getX()<this.getX() && b.getX() + ((Ball)b).getRadius() >= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length())
            {
            double dx = ((Ball)b).getRadius() - (this.getX() - b.getX());
            double dt = dx/(b.getXV());
            double dy = b.getYV() * dt;
            
            b.setX(b.getX() - dx);
            b.setY(b.getY() - dy);
            
            
            b.setXV(b.getXV() * -1);
            }
            if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - (this.getY() + length)) * (b.getY() - (this.getY()+length)) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
            {
            ((Ball)b).impactPoint(this.getX(), this.getY() + length);
            }
    
         }
         else
         {
            if(b.getY() < this.getY() &&  b.getY() + ((Ball)b).getRadius() >= this.getY() &&  b.getX() >= this.getX() && b.getX() <= this.getX() + this.length()) //ball hits from bottom
            {
            double dy = ((Ball)b).getRadius() - (this.getY() - b.getY());
            double dt = dy/(b.getYV());
            double dx = b.getXV() * dt;
            
            b.setY(b.getY() - dy);
            b.setX(b.getX() - dx);
            
            b.setYV(b.getYV() * -1);
            
            }
            if(b.getY() > this.getY() &&  b.getY() - ((Ball)b).getRadius() <= this.getY() &&  b.getX() >= this.getX() && b.getX() <= this.getX() + this.length())
            {
            double dy = ((Ball)b).getRadius() - (b.getY() - this.getY());
            double dt = dy/(b.getYV());
            double dx = b.getXV() * dt;
            
            b.setY(b.getY() + dy);
            b.setX(b.getX() - dx);
            
            b.setYV(b.getYV() * -1);
            }
             if((b.getX()-(this.getX()+length)) * (b.getX()-(this.getX()+length)) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
             {
             ((Ball)b).impactPoint(this.getX() + length, this.getY());
             }
            
         }
         if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
         {
         ((Ball)b).impactPoint(this.getX(), this.getY());
         }
      } 
      
   }

   public String toString()
   {
      return(this.getX()+" , "+this.getY()+" , "+isVertical);
   }

}