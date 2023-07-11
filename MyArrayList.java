    //create your own version of the ArrayList by completing this class definition
	 //look in ListInterface to see what methods you need to create
    
public class MyArrayList<anyType> 
{
   private Object[] list;		//stores the actual elements
   private int numElements;	//used to keep track of the number of valid elements in the list
   	
   public MyArrayList()
   {
      list = new Object[10];	//start with a buffer size of 10
      numElements = 0;
   }
   
   private void doubleCapacity()	//private because this is a helper method that need not be used outside of the class
   {
      	//make list twice as big, i.e. given [A, B, C, null], results with [A, B, C, null, null, null, null, null]
      	//to be used if we add an element that would be over the capacity of list
      Object[] nList = new Object[list.length*2];
      for(int i = 0; i<list.length; i++)
      {
         nList[i] = list[i];
      }
      list=nList;
   }
      
   private void cutCapacity()	//private because this is a helper method that need not be used outside of the class
   {
      	//make list half as big, i.e. given [A, B, C, null, null, null, null, null], results with [A, B, C, null]
      	//to be used if after removing an element, we have less than 1/3 of the capacity of list being used
      Object[] nList = new Object[list.length/2];
      for(int i = 0; i<nList.length; i++)
      {
         nList[i] = list[i];
      }
      list = nList;
   }
      
   public String toString()
   {
      String ans = "[";
      for(int i = 0; i<numElements; i++)
      {
         ans+=list[i].toString()+", ";
      }
      if(ans.length()>2)
         ans=ans.substring(0,ans.length()-2);
      	//add all array elements with a comma separating each, i.e. [A, B, C] 
      return ans + "]";
   }
   public boolean add(anyType x)
   {
      if(numElements == list.length)
         doubleCapacity();
      list[numElements]=x;
      numElements++;
      return true;
   }
   public boolean add(int index, anyType x)
   {
      if(numElements == list.length)
         doubleCapacity();
      for(int i = numElements; i>index; i--)
      {
         list[i]=list[i-1];
      }
      list[index]=x;
      numElements++;
      return true;
   }
   public int size()
   {
      return numElements;
   }
   public anyType set(int index, anyType x)
   {
      Object y = list[index];
      if(index<=numElements)
      {
         list[index]=x;
      }
      else
         throw new IndexOutOfBoundsException();
      return (anyType)y;
   }
   public anyType get(int index)
   {
      if(index<=numElements)
         return (anyType)list[index];
      else
         throw new IndexOutOfBoundsException();
   }
   public anyType remove(int index)
   {
      if(numElements==list.length)
         doubleCapacity();
      Object x = list[index];
      for(int i = index; i<numElements; i++)
      {
         list[i]=list[i+1];
      }
      numElements--;   
      if(numElements*3<=list.length)
         cutCapacity();
      
      return (anyType)x;
   }
}