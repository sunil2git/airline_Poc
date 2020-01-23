package poc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object airLinePocData {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Airline-POC")
      .getOrCreate()

    val airlineData = spark.read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/airline_data")
      .toDF("id", "airlineName", "alias", "IATA", "ICAO", "Callsign", "country", "active")

    val airportData = spark.read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/airport_data")
      .toDF("id", "airportName", "city", "country", "IATA/FAA", "ICAO", "latitude", "longitde", "Altitude", "Timezone", "DST", "place")

    val route = spark.read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/route_data")
      .toDF("airlineName", "airlineId", "source_airport", "source_airport_id", "destination_airport", "destination_airport_id", "CodeShare", "stops", "unknown")


    val result = oddEvenCheck(3)

    //data.groupBy(columnNames.Winner,columnNames.Stadiuam).agg(count("*").alias("cnt")).orderBy(col("cnt").desc).show()
    val source_airportDF = route.groupBy("source_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val destination_airportDF = route.groupBy("destination_airport").agg(count("*").alias("count")).orderBy(col("count").desc)

    val totalcount = source_airportDF.unionAll(destination_airportDF)

    val totalDF=totalcount.groupBy("source_airport").agg(sum("count").alias("totalcount")).orderBy(col("totalcount").desc).limit(5)

    totalDF.select("source_airport").show(truncate = false)

    airportData.join(totalDF, col("IATA/FAA") === col("source_airport")).select("airportName","city","country","IATA/FAA","totalcount").show()

  }

  def oddEvenCheck(num: Int): Boolean = {
    var res = true
    if (num % 2 == 0) {
      true
    }
    else {
      false
    }
  }
//test
}
