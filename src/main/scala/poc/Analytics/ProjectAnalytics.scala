package poc.Analytics


import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import poc.utils.ColumnNames


object ProjectAnalytics {

  /** Top 5 busy airline on top 10 busy route */

  def busyAirlineData(airlineData: DataFrame) = {

    val res = airlineData.groupBy(ColumnNames.AIRLINE_NAME).agg(count("*").alias("cnt"))
      .orderBy(col("cnt").desc).limit(5)
    res
  }

  /** (i) Top 5 busy airline on top 5 busy airport
   * using Route data, Less description  */

  def busyAirport(routeData: DataFrame): Dataset[Row] = {
    val source_airportDF = routeData.groupBy("source_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val destination_airportDF = routeData.groupBy("destination_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val totalcount = source_airportDF.unionAll(destination_airportDF)
    val res = totalcount.groupBy("source_airport").agg(sum("count").alias("totalcount")).orderBy(col("totalcount").desc).limit(5)
    res
  }

  /** (ii) Top 5 busy airline on top 5 busy airport
   * using airline data and route data for more description about airport */

  def busyAirport(routeData: DataFrame, airportData: DataFrame): Dataset[Row] = {
    val source_airportDF = routeData.groupBy("source_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val destination_airportDF = routeData.groupBy("destination_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val totalcount = source_airportDF.unionAll(destination_airportDF)
    val totalDF = broadcast(totalcount.groupBy("source_airport").agg(sum("count").alias("totalcount")).orderBy(col("totalcount").desc).limit(5))

    val res = airportData.join(totalDF, col("iata_faa") === col("source_airport")).select("airportname", "city", "country", "iata_faa", "totalcount")
      .orderBy(col("totalcount").desc)
    res
  }

  def busyAirportUDF(routeData: DataFrame, airportData: DataFrame): Dataset[Row] = {
    val source_airportDF = routeData.groupBy("source_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val destination_airportDF = routeData.groupBy("destination_airport").agg(count("*").alias("count")).orderBy(col("count").desc)
    val totalcount = source_airportDF.unionAll(destination_airportDF)
    val totalDF = broadcast(totalcount.groupBy("source_airport").agg(sum("count").alias("totalcount")).orderBy(col("totalcount").desc).limit(5))

    val joinedData = airportData.join(totalDF, col("iata_faa") === col("source_airport")).select("airportname", "city", "country", "iata_faa", "totalcount")
      .orderBy(col("totalcount").desc)
    val upper: String => String = _.toUpperCase
    val upperUDF = udf(upper)
    val res = joinedData.withColumn("airportname",upperUDF(col("airportname")))
    res
  }
}
