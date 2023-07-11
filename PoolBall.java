public class PoolBall extends Ball
{
private int num;

public PoolBall(double x, double y, int n)
{
super(20, x, y, 10,"images/"+ n + "ball.png");
num = n;




}

public boolean isSolid()
{
if(num >=9)
return false;
return true;
}
public int getNum()
{
return num;
}


}