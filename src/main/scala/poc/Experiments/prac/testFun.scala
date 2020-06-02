package poc.Experiments.prac

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Window
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{sum, _}
import java.text.SimpleDateFormat


object testFun {


  def salaryIncrement1(df : DataFrame) =
  {

    val row_dept = df.select(df("*"),row_number().over(Window.partitionBy(col("dept")).orderBy(col("sal").desc)).alias("Row_Num"))
    row_dept.filter( row_dept("Row_Num")==="3")
  }

}
