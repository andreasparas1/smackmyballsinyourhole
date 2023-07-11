//create your own version of the ArrayList by completing this class definition
//look in ListInterface to see what methods you need to create
class MyArrayList<anyType> {
    private var list //stores the actual elements
            : Array<Any?>
    private var numElements //used to keep track of the number of valid elements in the list
            = 0

    init {
        list = arrayOfNulls(10) //start with a buffer size of 10
    }

    private fun doubleCapacity() //private because this is a helper method that need not be used outside of the class
    {
        //make list twice as big, i.e. given [A, B, C, null], results with [A, B, C, null, null, null, null, null]
        //to be used if we add an element that would be over the capacity of list
        val nList = arrayOfNulls<Any>(list.size * 2)
        for (i in list.indices) {
            nList[i] = list[i]
        }
        list = nList
    }

    private fun cutCapacity() //private because this is a helper method that need not be used outside of the class
    {
        //make list half as big, i.e. given [A, B, C, null, null, null, null, null], results with [A, B, C, null]
        //to be used if after removing an element, we have less than 1/3 of the capacity of list being used
        val nList = arrayOfNulls<Any>(list.size / 2)
        for (i in nList.indices) {
            nList[i] = list[i]
        }
        list = nList
    }

    override fun toString(): String {
        var ans = "["
        for (i in 0 until numElements) {
            ans += list[i].toString() + ", "
        }
        if (ans.length > 2) ans = ans.substring(0, ans.length - 2)
        //add all array elements with a comma separating each, i.e. [A, B, C] 
        return "$ans]"
    }

    fun add(x: anyType): Boolean {
        if (numElements == list.size) doubleCapacity()
        list[numElements] = x
        numElements++
        return true
    }

    fun add(index: Int, x: anyType): Boolean {
        if (numElements == list.size) doubleCapacity()
        for (i in numElements downTo index + 1) {
            list[i] = list[i - 1]
        }
        list[index] = x
        numElements++
        return true
    }

    fun size(): Int {
        return numElements
    }

    operator fun set(index: Int, x: anyType): anyType? {
        val y = list[index]
        if (index <= numElements) {
            list[index] = x
        } else throw IndexOutOfBoundsException()
        return y as anyType?
    }

    operator fun get(index: Int): anyType? {
        return if (index <= numElements) list[index] as anyType? else throw IndexOutOfBoundsException()
    }

    fun remove(index: Int): anyType? {
        if (numElements == list.size) doubleCapacity()
        val x = list[index]
        for (i in index until numElements) {
            list[i] = list[i + 1]
        }
        numElements--
        if (numElements * 3 <= list.size) cutCapacity()
        return x as anyType?
    }
}