package poc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}
import poc.utils.columnNames
import com.datastax.spark.connector._
import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._





object airLinePocData {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Airline-POC")
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .getOrCreate()

    val airlineData = spark.read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/airline_data")
      .toDF(columnNames.AIRLINE_ID, columnNames.AIRLINE_NAME, columnNames.ALIAS, columnNames.IATA, columnNames.ICAO, columnNames.CALLSIGN, columnNames.COUNTRY, columnNames.ACTIVE)

    val airportData = spark.read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/airport_data")
      .toDF("id", "airportName", "city", "country", "IATA/FAA", "ICAO", "latitude", "longitude", "Altitude", "Timezone", "DST", "place")

    val route = spark.read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/route_data")
      .toDF(columnNames.AIRLINE_NAME, columnNames.AIRLINE_ID, columnNames.SOURCE_AIRPORT, columnNames.SOURCE_AIRPORT_ID, columnNames.DESTINATION_AIRPORT, columnNames.DESTINATION_AIRPORT_ID, columnNames.CODESHARE, columnNames.STOPS, columnNames.UNKNOWN)


    val result = oddEvenCheck(3)
    //data.groupBy(columnNames.Winner,columnNames.Stadiuam).agg(count("*").alias("cnt")).orderBy(col("cnt").desc).show()
    val source_airportDF = route.groupBy("source_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val destination_airportDF = route.groupBy("destination_airport").agg(count("*").alias("count")).orderBy(col("count").desc)

    val totalcount = source_airportDF.unionAll(destination_airportDF)

    val totalDF = totalcount.groupBy("source_airport").agg(sum("count").alias("totalcount")).orderBy(col("totalcount").desc).limit(10)

    //  totalDF.select("source_airport").show(truncate = false)
    /** Top 10 busy route */
    airportData.join(totalDF, col("IATA/FAA") === col("source_airport")).select("airportName", "city", "country", "IATA/FAA", "totalcount")
      .orderBy(col("totalcount").desc)
    //.show(truncate = false)

    /** Top 10 busy airline on top 10 busy route */


    airlineData.groupBy(columnNames.AIRLINE_NAME).agg(count("*").alias("cnt"))
      .orderBy(col("cnt").desc).limit(5)

    route.groupBy(columnNames.SOURCE_AIRPORT).agg(count("*").alias("cnt"))
      .orderBy(col("cnt").desc).limit(5)

    route.groupBy(columnNames.AIRLINE_NAME, columnNames.SOURCE_AIRPORT, columnNames.DESTINATION_AIRPORT).agg(count("*").alias("cnt"))
      .orderBy(col("cnt").desc).limit(5)

    route.groupBy(columnNames.AIRLINE_ID, columnNames.AIRLINE_NAME, columnNames.DESTINATION_AIRPORT, columnNames.SOURCE_AIRPORT).agg(count("*").alias("cnt"))
      .orderBy(col("cnt").desc).limit(5)

    import spark.implicits._

    val testrouteDF = Seq(("A", "pune", "goa"), ("B", "pune", "goa"), ("B", "goa", "pune"), ("C", "pune", "mumbai"), ("A", "mumbai", "pune"), ("A", "goa", "pune"), ("B", "mumbai", "pune")
      , ("D", "mumbai", "goa"), ("B", "goa", "mumbai"))
      .toDF("person", "place1", "place2")

    testrouteDF.createTempView("temp")

    spark.sql("select * from temp t1 join temp t2 on t1.place1 =t2.place2 where t1.place2 =t2.place1")

    spark.sql("select place1,place2,count(*) from temp group by place1,place2 ")

    val test = testrouteDF.groupBy(col("place1"), col("place2")).agg(count("*").alias("cnt"))

    test//.show()

    test.createTempView("test")

    val test1 = spark.sql("select Distinct(*),case when t1.place1= t2.place2 and t2.place1=t1.place2 then t1.cnt + t2.cnt else 0 end as routeCount from test t1 join test t2  on t1.place1= t2.place2 and t2.place1=t1.place2")//.show() //
     airlineData.agg(count(col("*")))

    /** Read Data from Cassandra Tables */

    val airlinedataDF = spark
      .read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "airlinedata", "keyspace" -> "airlinepoc")).load.cache()

    val routeDF = spark
      .read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "route", "keyspace" -> "airlinepoc")).load.cache()

    val airportdataDF = spark
      .read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "airportdata", "keyspace" -> "airlinepoc")).load.cache()

    airportdataDF.show(numRows = 5)

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

}
