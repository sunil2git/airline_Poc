package poc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

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


    route.show()

  }
}
