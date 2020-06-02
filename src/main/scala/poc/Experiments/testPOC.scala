package poc.Experiments

import java.io.{File, PrintWriter}
import java.text.SimpleDateFormat
import java.util.regex.Pattern
import java.util.{Calendar, Date}

import org.apache.spark.sql.expressions.Window
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{sum, _}
import shapeless.ops.coproduct.Folder
import poc.Experiments.utils

import scala.util.matching.Regex

object testPOC {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = utils.sparkSession()



    import spark.implicits._

    println("sparkSession test program")

    val emp = Seq(
      (1,"sk",5000,1),
      (2,"ss",4000,1),
      (3,"rk",3400,2),
      (4,"rr",4000,2),
      (5,"pk",6000,3),
      (6,"pp",4500,3),
      (7,"pp",7000,4),
      (8,"sam",7000,4),
      (8,"rick",7500,4)).toDF("id","name","sal","dept")

    val dept=Seq((1,"sales","pune"),(2,"marketing","mumbai"),(3,"IT","hyd"),(4,"Management","delhi")).toDF("deptIt","deptName","city")

    //emp.show()
    // dept.show()

    emp.join(dept,$"dept"===$"deptIt").where($"city"==="pune")
    emp.orderBy($"sal".desc).limit(1)

    val row_dept = emp.select(emp("*"),row_number().over(Window.partitionBy(col("dept")).orderBy(col("sal").desc)).alias("Row_Num"))
    //row_dept.
    // row_dept.show()
    //row_dept.filter( row_dept("Row_Num")==="3").show()


    def salaryIncrement(df : DataFrame) =
    {
      val result=df.withColumn("updatedSal", when($"dept"=== "1", ($"sal"*.3+$"sal"))
        .when($"dept"=== "2", ($"sal"*.35+$"sal"))
        .when($"dept"=== "3", ($"sal"*.4+$"sal"))
        .otherwise($"sal"))
      result
    }

    val incrementSalDf=salaryIncrement(emp)

    incrementSalDf.show()
    println(Console.RED+" Digital Desire : ")

    //incrementSalDf1.show()






  }
}

