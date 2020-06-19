package poc.Experiments.rockJvm.basic

import scala.annotation.tailrec

object FunctionInDeep extends App {

  // recursion function
  // note : recursion function always need the return type explicitly mentioned. like here : String

  def recursionDemo(str : String, n :Int) : String ={
    if(n == 1) str
    else str + recursionDemo(str, n-1)
  }
  println(recursionDemo("test",3))

  def factrialRecursion(num: Int) : Int = {
   if(num <=1) num
    else num * factrialRecursion(num - 1)
  }
  println(factrialRecursion(5))

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
    // use helper function and accumulator variable to hold summation result
    // provide annotation of tailRec

  def factorialTailRec(n : Int) : Int =  {
      @tailrec
      def factHelper(x : Int, accumulator : Int): Int = {
        if (x <=1 ) accumulator
         else factHelper(x-1, x * accumulator) // TAIL RECURSION =  use recursive call as the last expression
      }

      factHelper(n,1)
    }

  println(factorialTailRec(5))





  // recursion function
  // note : recursion function always need the return type explicitly mentioned. like here : String
  // note : unit is return type for the print any value in class loading or write data into table or file.


}
