package poc.Analytics


import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, count, sum}
import poc.utils.columnNames

object projectAnalytics {

  /** Top 5 busy airline on top 10 busy route */

  def busyAirlineData(aiirlineData: DataFrame) = {

    val res = aiirlineData.groupBy(columnNames.AIRLINE_NAME).agg(count("*").alias("cnt"))
      .orderBy(col("cnt").desc).limit(5)
    res
  }

  /** (i) Top 5 busy airline on top 5 busy airport
   * using Route data, Less description  */

  def busyAirport1(routeData: DataFrame) = {
    val source_airportDF = routeData.groupBy("source_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val destination_airportDF = routeData.groupBy("destination_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val totalcount = source_airportDF.unionAll(destination_airportDF)
    val res = totalcount.groupBy("source_airport").agg(sum("count").alias("totalcount")).orderBy(col("totalcount").desc).limit(5)
    res
  }

  /** (ii) Top 5 busy airline on top 5 busy airport
   * using airline data and route data for more description about airport */

  def busyAirport2(routeData: DataFrame, airportData: DataFrame) = {
    val source_airportDF = routeData.groupBy("source_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val destination_airportDF = routeData.groupBy("destination_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val totalcount = source_airportDF.unionAll(destination_airportDF)
    val totalDF = totalcount.groupBy("source_airport").agg(sum("count").alias("totalcount")).orderBy(col("totalcount").desc).limit(5)

    val res = airportData.join(totalDF, col("IATA/FAA") === col("source_airport")).select("airportName", "city", "country", "IATA/FAA", "totalcount")
      .orderBy(col("totalcount").desc)
    res
  }
}
