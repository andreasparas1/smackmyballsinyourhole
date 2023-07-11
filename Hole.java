public class Hole extends Entity
{
   private double radius;


   public Hole(double x, double y, double r)
   {
      super(0, x, y, 0, 0, 0, 0, "images/8ball.png");
      radius = r;
   }

   public double getRadius()
   {
      return radius;
   }

   public boolean checkCollisions(Entity b)
   {
      if(b instanceof Ball)
      {
      
      
         return (this.getX() - b.getX()) * (this.getX() - b.getX()) + (this.getY() - b.getY()) * (this.getY() - b.getY()) < radius * radius;
      
      
      }
      return false;
   }
   
   public void impact(Entity b)
   {
   if(b instanceof Ball)
   {
   ((Ball)b).setRadius(11);
   }
   
   
   }
}