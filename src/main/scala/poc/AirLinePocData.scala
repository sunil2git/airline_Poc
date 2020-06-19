package poc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import poc.Analytics.ProjectAnalytics
import poc.utils.ProjectUtils
import poc.utils.ProjectUtils.sparkSession


object AirLinePocData {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)


    val conf = new SparkConf().setMaster("local[*]").setAppName("kafka-test").set("spark.cassandra.connection.host", "127.0.0.1")
    val ssc = new StreamingContext(conf, Seconds(5))

    val spark: SparkSession = ProjectUtils.sparkSession()

    val airlineData: DataFrame = ProjectUtils.airlineData()

    val routeData: DataFrame = ProjectUtils.routeData()

    val airportData: DataFrame = ProjectUtils.airportData()


    val test = ProjectAnalytics.busyAirport(routeData, airportData).limit(10)


    val configData = sparkSession().read.option("multiline", true).json("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/main/scala/poc/config.json")
    val airlineDataPath = configData.select("airlineDataPath").collect()(0).mkString("")
    println(airlineDataPath)
    //airlineData.show(numRows = 5)

    airportData.show(numRows = 5)



    // test.show(numRows = 5)
    //projectUtils.csvWriter(test)
    println(Console.GREEN + " Data inserted ****")


    // StreamingUtils.showStreamingData()


    /** Read Data from Cassandra Tables */

    // Issue in loading data in airlineData table

    ProjectUtils.airlinDataCqlsh() //.show(numRows = 5)
    ProjectUtils.routeDataCqlsh() //.show(numRows = 5)
    ProjectUtils.airportDataCqlsh //.show(numRows = 5)


  }


}
