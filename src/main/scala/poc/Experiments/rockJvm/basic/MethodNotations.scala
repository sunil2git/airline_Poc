package poc.Experiments.rockJvm.basic

object MethodNotations extends App {

  class Person(val name : String , favoriteMovie : String, val age : Int = 0) {
    def likes(movie : String) : Boolean = movie == favoriteMovie
    def hangOutWith(person : Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(person : Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(nickname : String) : Person = new Person(s"$name($nickname)",favoriteMovie)
    def unary_! : String = s"$name, what the heck!"
    def unary_+ : Person = new Person(name, favoriteMovie,age + 1)
    def  isAlive() :Boolean = true
    def apply() :String =s"Hi name is $name and favorite movie is $favoriteMovie"
    def apply( n :Int) :String = s"$name watched $favoriteMovie $n times"

    def learns(thing :String): String =s"$name is learning $thing"
    def learnScala = this learns "Scala"

  }

   val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")

  //Infix notation = operator notation (Syntactic Sugar)

  //operators in scala
  val tom =new Person("Tom","Fight Club")
  println(mary hangOutWith(tom))
  println(mary hangOutWith tom)
  println(mary.+(tom))
  println(mary + tom)

  println(1 + 2)
  println(1.+(2))
  // ALL OPERATORS ARE METHODS.

 // Prefix notation.
  val x = -1 // equivalent with 1.unary_-
  val y =1.unary_-

  println(!mary)
  println(mary.unary_!)
  // unary_ prefix only works with - + ~ !

  println(mary.isAlive())
  println(mary isAlive)

  println(mary.apply())
  print(mary apply)
  println(mary())
  // Apply method

  /*
  1. Overload the + operator
    mary + "the rockstar" => new Person "Marry (the rockstar)"

   2. Add an age to the Person class
      Add a unary  + operator => new Person with the age + 1
      +mary => mary with the age incrementer

   3. Add a "learns" methods in the Person class => "Mary learns Scala"
      Add a learnScala method, calls learns method with "Scala"
      Use it in postfix notation.

   4. Overload the apply method
      mary.apply(2) => "Mary watched Inception 2 times "
  */

   println((mary + "the RockStar")())
   println((+mary).age)
   print(mary learnScala)
  println(mary(10))

  /*
  takeaways
   1. infix notation mary.likes("Inception") or mary likes "Inception"
      only works methods which have only one parameter
   2. prefix notation !mary or mary.unary_! ex:
      only allow in this operators +,-,~,!
   3. Postfix notation mary.isAlive or mary isAlive
      rarely used in practice because they may create confusion while reading the code.
  */






}
