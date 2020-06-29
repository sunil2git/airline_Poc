package poc.Experiments.rockJvm.basic

import scala.annotation.tailrec

object FunctionInDeep extends App {

  // recursion function
  // note : recursion function always need the return type explicitly mentioned. like here : String

  def recursionDemo(str : String, n :Int) : String ={
    if(n == 1) str
    else str + recursionDemo(str, n-1)
  }
 // println(recursionDemo("test",3))

  def factrialRecursion(num: Int) : Int = {
   if(num <=1) num
    else num * factrialRecursion(num - 1)
  }
//  println(factrialRecursion(5))

  def fibonnaciRecursion(num : Int) : Int =
  if(num <=2)   1
  else fibonnaciRecursion(num-1) + fibonnaciRecursion(num-2)
  // without any expression braces

 // println(fibonnaciRecursion(8))

  def isPrime(num:Int) : Boolean = {
    def isPrimeUntil(t:Int) : Boolean =
      if(t <=1 ) true
        else num % t !=0 && isPrimeUntil(t-1)
    isPrimeUntil(num /2)
  }

 // println(isPrime(5))

  /** Tail Recursion */
    // use helper function and accumulator variable to hold summation result.
    // provide annotation of tailRec
    // Outer Function only take the the number as input and task done by the helper function which is kind of tailrec Function.
    // Last statement, most of the times a/c logic will be the helper or inner Function. thats why its called as recursion.

  def factorialTailRec(n : Int) : Int =  {
      @tailrec
      def factHelper(x : Int, accumulator : Int): Int = {
        if (x <=1 ) accumulator
         else factHelper(x-1, x * accumulator) // TAIL RECURSION =  use recursive call as the last expression
      }

      factHelper(n,1)
    }

  //println(factorialTailRec(5))

   @tailrec
   def concatenateTailRec(str: String,n : Int , accumulator: String) : String =
     if (n <=0 ) accumulator
   else concatenateTailRec(str, n-1, accumulator + str)

 // println(concatenateTailRec("hello",3,""))

  def isPrimeNo( n : Int) : Boolean = {

    @tailrec
    def isPrimeTailRec( t : Int , isStillBoolean : Boolean) : Boolean =
      if(! isStillBoolean) false
      else if (t <=1 ) true
      else isPrimeTailRec(t-1,n % t != 0 && isStillBoolean)

     isPrimeTailRec(n/2,true)
  }

  //println(isPrimeNo(7))

  def fibonacciNo( n : Int) : Int ={
    def fiboTailRec(i :Int,lastNo : Int, nextToLast : Int) : Int =
      if (i >=n) lastNo
      else fiboTailRec(i+1,lastNo + nextToLast, lastNo)
     if (n <=2) 1
     else fiboTailRec(2,1,1)
  }
  //println(fibonacciNo(1))

  // recursion function
  // note : recursion function always need the return type explicitly mentioned. like here : String
  // note : unit is return type for the print any value in class loading or write data into table or file.

  /** Call by value and call by name */

    // : => DataType is Syntax to declare the call by name
    // used to delay the computation, Did computation when required and not pass as it is value of variables or value in scala.
    // ": DataType" Call by value : value is computed before the function is call and used in entire program.

    def callByValue(time : Long): Unit = {
      /*println(time)
      println(time)*/
    }

  def callByName(time: => Long): Unit = {
 /*   println(time)
    println(time)*/
  }

  callByValue(System.nanoTime())
  callByName(System.nanoTime())
    
  def infinite() : Int = 1 + infinite()
  def printFirst(x : Int, y: => Int)  = println(x)

  // printFirst(20,infinite())

  /** Default Argument  */

    @tailrec
  def factorial(n : Int, acc: Int = 1) : Int =
    if(n<=1) acc
      else factorial(n-1,n * acc)

  println(factorial(5))
  def savePicture(format : String = "jpg", height : Int =200, width: Int = 400 ) : Unit = println("Saving Picture")

  println(savePicture(height = 200)) // we need to specify the leading parameter to end or specify the parameter name.

  /** Strings
   * s-interpolators
   * F-interpolators
   * raw-interpolators
   * */

  val name = "sunil"
  val res = s"$name is a software developer." // String interpolator

  val floatNum = 1.2f
  val res1 = f" result $floatNum%2.2f" // Float interpolator
  val escapChar = "That is an \n newline"
  val res3 = raw"$escapChar" // Raw interpolator : will use the the escaped char and not string as it is.











}
