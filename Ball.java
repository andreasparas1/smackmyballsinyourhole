public class Ball extends Entity
{
   private double radius;


   public Ball(double m, double x, double y, double r)
   {
      super(m, x, y, 0, 0, 0, 0, "images/8ball.png");
      radius = r;
   }
   public Ball(double m, double x, double y, double r, String i)
   {
      super(m, x, y, 0, 0, 0, 0, i);
      radius = r;
   }


   public boolean checkCollisions(Entity b)
   {
      if(b instanceof Ball)
      {
      
         return (this.getX()-b.getX())*(this.getX()-b.getX()) + (this.getY()-b.getY())*(this.getY()-b.getY()) <= (this.getRadius()+((Ball)(b)).getRadius())*(this.getRadius()+((Ball)(b)).getRadius());
      }
      else if(b instanceof Wall)
      {
         return b.checkCollisions(this); //temp statement to keep compiling
      }
      else if(b instanceof Hole)
      {
         return b.checkCollisions(this);
      }
      else
      {
         return false;
      }
   }

   public double getRadius()
   {
      return radius;
   }
   
   public void unstick(Entity b)
   {
      if(b instanceof Ball)
      {
      
         if((this.getX()-b.getX())*(this.getX()-b.getX()) + (this.getY()-b.getY())*(this.getY()-b.getY()) < (this.getRadius()+((Ball)(b)).getRadius())*(this.getRadius()+((Ball)(b)).getRadius())) //balls have clipped into each other
         {
            double change = (this.getRadius() + ((Ball)b).getRadius()) - Math.sqrt((this.getX()-b.getX())*(this.getX()-b.getX()) + (this.getY()-b.getY())*(this.getY()-b.getY()));
            double change1 = change * (this.getMass()/(this.getMass() + b.getMass()));
            double change2 = change * (b.getMass()/(this.getMass() + b.getMass()));
            
            double w = Math.atan((this.getY()-b.getY())/(this.getX()-b.getX()));
            
            if(this.getX()>b.getX())
            {
               this.setX(this.getX() + Math.abs(change1 * Math.cos(w)));
               b.setX(b.getX() - Math.abs(change2 * Math.cos(w)));
            }
            else
            {
               this.setX(this.getX() - Math.abs(change1 * Math.cos(w)));
               b.setX(b.getX() + Math.abs(change2 * Math.cos(w)));
            }
            if(this.getY()>b.getY())
            {
               this.setY(this.getY() + Math.abs(change1 * Math.sin(w)));
               b.setY(b.getY() - Math.abs(change2 * Math.sin(w)));
            }
            else
            {
               this.setY(this.getY() - Math.abs(change1 * Math.sin(w)));
               b.setY(b.getY() + Math.abs(change2 * Math.sin(w)));
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
   
   public void impact(Entity b)
   {
      if(b instanceof Ball)
      {
         
         this.unstick(b);
         
         double X1 = this.getX();
         double Y1 = this.getY();
         double XV1 = this.getXV();  //values of this ball
         double YV1 = this.getYV();
         double M1 = this.getMass();
         
         
         double X2 = b.getX();
         double Y2 = b.getY();
         double XV2 = b.getXV();  //values of colliding ball
         double YV2 = b.getYV();
         double M2 = b.getMass();  
         
         double w = Math.atan((Y2-Y1)/(X2-X1));  //angle between ball centers
         
         
         
         
         double XV = XV1 - XV2;   //values of momentum from perspective of second ball being still
         double YV = YV1 - YV2;   
         
         
         
         double w2 = w;
         if(X2-X1<0)
            w2+=Math.PI;
         
         double t = Math.atan(YV/XV); //angle of velocity vector
         
         double t2 = t;
         if(XV<0)
            t2+=Math.PI;
         
         double impulse = (-2*Math.sqrt(XV*XV+YV*YV)*Math.cos(t2-w2))/(1/M1+1/M2);
         
         this.addSpeed(impulse/M1, w2);
         
         b.addSpeed(impulse/M2, w2+Math.PI);
         
         
         
         
      }
      else if(b instanceof Wall)
      { 
         b.impact(this);
      }
      else if(b instanceof Hole)
      {
         b.impact(this);
      }
   }
   
   public void impactPoint(double X2, double Y2) // ball bounces off of a static point
   {
      double XV = this.getXV();
      double YV = this.getYV();
   
      double X1 = this.getX();
      double Y1 = this.getY();
   
      double t = Math.atan(YV/XV); //angle of velocity vector
         
      double t2 = t;
      if(XV<0)
         t2+=Math.PI;
         
      double w = Math.atan((Y2-Y1)/(X2-X1));
      double w2 = w;
      if(X2-X1<0)
         w2+=Math.PI; 
      double impulse = (2 * Math.sqrt(XV*XV+YV*YV)*Math.cos(t2-w2));
   
      this.addSpeed(impulse, w2+Math.PI);
   }
   
   
   public void setRadius(double a)
   {
   radius = a;
   }
   
   
   public String toString()
   {
      return(this.getX() + " , "+this.getY()+" , "+this.getRadius());
   }
}