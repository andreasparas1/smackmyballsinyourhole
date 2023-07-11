import java.awt.Color;
public class PoolWall extends Wall
{
   private boolean collidingSide; // if true and wall is verticle ball collides from left, if true and wall is horizontal ball collides from bottom

   public PoolWall(double x, double y, double l, boolean isV, Color c, boolean cs)
   {
      super(x, y, l, isV, c);
      collidingSide = cs;
   }
   @Override
   public boolean checkCollisions(Entity b)
   {
      if(b instanceof Ball)
      {
         if(this.isVert())
         {
            if(collidingSide)//ball hits wall from left
            {
               if(b.getX() + ((Ball)b).getRadius() >= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length())
                  return true;
            
            }
            else //ball hits wall from right
            {
               if(b.getX() - ((Ball)b).getRadius() <= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length())
                  return true;
            
            }
            if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - (this.getY() + this.length())) * (b.getY() - (this.getY()+this.length())) < ((Ball)b).getRadius() * ((Ball)b).getRadius())  //Ball hits top edge
               return true;
         
         }
         else
         {
            if(collidingSide)//hits from bottom
            {
               if(b.getY() + ((Ball)b).getRadius() >= this.getY() && b.getX() >= this.getX() && b.getX() <= this.getX() + this.length())
                  return true;
            }
            else //hits from top
            {
               if(b.getY() - ((Ball)b).getRadius() <= this.getY() && b.getX() >= this.getX() && b.getX() <= this.getX() + this.length())
                  return true;
            }
            if((b.getX()-(this.getX()+this.length())) * (b.getX()-(this.getX()+this.length())) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
               return true;
         }
         if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
            return true;
      
      
      }
      else
         return false;
      return false;
   }
   
   public void impact(Entity b)
   {
   if(b instanceof Ball)
      {
         if(this.isVert())
         {
            if(collidingSide)//ball hits wall from left
            {
               if(b.getX() + ((Ball)b).getRadius() >= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length())
               {
               double dx = ((Ball)b).getRadius() - (this.getX() - b.getX());
               double dt = dx/(b.getXV());
               double dy = b.getYV() * dt;
                  
                  b.setX(b.getX() - dx); 
                  b.setY(b.getY() - dy);
                  b.setXV(b.getXV() * -1);
               }
               
            
            }
            else //ball hits wall from right
            {
               if(b.getX() - ((Ball)b).getRadius() <= this.getX() && b.getY() >= this.getY() && b.getY() <= this.getY() + this.length())
                  {
                  double dx = ((Ball)b).getRadius() - (b.getX() - this.getX());
                  double dt = dx/(b.getXV());
                  double dy = b.getYV() * dt;
                  
                  b.setX(b.getX() + dx); 
                  b.setY(b.getY() - dy);
                  b.setXV(b.getXV() * -1);                  
                  }
            
            }
            if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - (this.getY() + this.length())) * (b.getY() - (this.getY()+this.length())) < ((Ball)b).getRadius() * ((Ball)b).getRadius())  //Ball hits top edge
               {
               ((Ball)b).impactPoint(this.getX(), this.getY() + this.length());
               }
         
         }
         else
         {
            if(collidingSide)//hits from bottom
            {
               if(b.getY() + ((Ball)b).getRadius() >= this.getY() && b.getX() >= this.getX() && b.getX() <= this.getX() + this.length())
                  {
                  double dy = ((Ball)b).getRadius() - (this.getY() - b.getY());
                  double dt = dy/(b.getYV());
                  double dx = b.getXV() * dt;
                  b.setX(b.getX() - dx);
                  b.setY(b.getY() - dy);
                  b.setYV(b.getYV() * -1);
                  }
            }
            else //hits from top
            {
               if(b.getY() - ((Ball)b).getRadius() <= this.getY() && b.getX() >= this.getX() && b.getX() <= this.getX() + this.length())
                  {
                  double dy = ((Ball)b).getRadius() - (b.getY() - this.getY());
                  double dt = dy/(b.getYV());
                  double dx = b.getXV() * dt;
                  b.setX(b.getX() - dx);
                  b.setY(b.getY() + dy);
                  b.setYV(b.getYV() * -1);
                  }
            }
            if((b.getX()-(this.getX()+this.length())) * (b.getX()-(this.getX()+this.length())) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
               {
               ((Ball)b).impactPoint(this.getX() + this.length(), this.getY());
               }
         }
         if((b.getX()-this.getX()) * (b.getX()-this.getX()) + (b.getY() - this.getY()) * (b.getY() - this.getY()) < ((Ball)b).getRadius() * ((Ball)b).getRadius())
         {
         ((Ball)b).impactPoint(this.getX(), this.getY());
         }
      
      
      }
   
   
   }

}