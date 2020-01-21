package poc
import org.apache.spark.sql.expressions.Window
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{sum, _}

object airLinePocData {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("ColumnWork")
      .getOrCreate()
    import spark.implicits._

    // println(" test scala program")

    case class Song(title:String,artist:String,track:Int)
    val stay=Song("Stay","Inna",4)
    //  println(s"Hello, 2+3 is ${2+3}")

    val df = Seq(
      (1, "2014/01/01 23:00:01"),
      (1, "2014/11/31 12:40:32"),
      (1, "2016/12/29 09:54:00"),
      (1, "2016/05/09 10:12:43")).toDF("id", "date")

    // df.show()




    def sum(a:Int)(b:Int)(c:Int)={
      a+b+c}
    val sumValue= sum(3)(2)(1)
    // println(sumValue)

    val data = Seq(
      (1, "a","jan","10"),
      (1, "b","jan","10"),
      (2, "c","jan","10"),
      (2, "a","jan","5")).toDF("id", "physician","month","value")

    data.createTempView("test")

    spark.sql("select t1.*, t1.value/t2.value from test t1 join test t2 on t1.physician=t2.physician where t1.id != t2.id").show

  }



}
