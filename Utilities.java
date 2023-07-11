import java.awt.*;
import javax.swing.*;
public class Utilities
{

   public static double distance(double x1, double y1, double x2, double y2)
   {
   
      return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
   
   
   }


   public static void main(String[]args)
   {
      Ball a = new Ball(10, 14, 28, 10);
      Ball b = new Ball(10, 19, 9, 10);
      b.setYV(1);
      System.out.println(Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY())));
      a.unstick(b);
      System.out.println(Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY())));
   }

   
}