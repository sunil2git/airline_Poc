package poc.Experiments
import org.apache.spark.sql.SparkSession
import poc.Experiments.testPOC
import poc.utils.columnNames

object utils {

  def sparkSession () ={
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("test saprkSession")
      .getOrCreate()
    spark
  }



}
