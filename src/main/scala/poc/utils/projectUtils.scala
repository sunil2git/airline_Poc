package poc.utils

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object projectUtils {

  /** Developer name : Sunil kumar  */


  /** spark session configuration, Entry point to spark :  */

  def sparkSession() =
    {
       val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("test sparkSession")
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .getOrCreate()
      spark
    }

  /** Loading Data into respective dataFrames :   */

  def airlineData() = {
    val airlineData = sparkSession().read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/airline_data")
      .toDF(columnNames.AIRLINE_ID, columnNames.AIRLINE_NAME, columnNames.ALIAS, columnNames.IATA, columnNames.ICAO, columnNames.CALLSIGN, columnNames.COUNTRY, columnNames.ACTIVE).cache()
    airlineData
  }

  def routeData() = {
    val routeData = sparkSession().read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/route_data")
      .toDF(columnNames.AIRLINE_NAME, columnNames.AIRLINE_ID, columnNames.SOURCE_AIRPORT, columnNames.SOURCE_AIRPORT_ID, columnNames.DESTINATION_AIRPORT, columnNames.DESTINATION_AIRPORT_ID, columnNames.CODESHARE, columnNames.STOPS, columnNames.UNKNOWN)
    routeData
  }

  def airportData() = {
    val airportData = sparkSession().read.csv("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/Docs&Data/airport_data")
      .toDF("id", "airportName", "city", "country", "IATA/FAA", "ICAO", "latitude", "longitude", "Altitude", "Timezone", "DST", "place")
    airportData
  }

  /** Loading Data into respective dataFrames from Cassandra Database :   */

  def airlinDataCqlsh() = {
    val airlinedataDF = sparkSession()
      .read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "airlinedata", "keyspace" -> "airlinepoc")).load.cache()
    airlinedataDF
  }

  def routeDataCqlsh() = {

    val routeDF = sparkSession().read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "route", "keyspace" -> "airlinepoc")).load.cache()
    routeDF
  }

  def airportDataCqlsh = {
    val airportDF = sparkSession().read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "airportdata", "keyspace" -> "airlinepoc")).load.cache()
    airportDF
  }


  /** Writing data into output file :  */

  // airlineData.write.format("org.apache.spark.sql.cassandra").options(Map( "table" -> "airlinedata", "keyspace" -> "airlinepoc")).save()
  //  Below is tested for writing.
  //  airlineData.write.format("org.apache.spark.sql.cassandra").options(Map("table" -> "airlinedata", "keyspace" -> "airlinepoc")).mode(SaveMode.Append).save()
  // res.write.format("csv").save("/Users/acs/Documents/sparkData/res/demo")

  def csvWriter(df: DataFrame) = {
    df.write.format("csv").save("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/main/scala/poc/outputFiles/file_1.1")
  }

  def saveInCassandraTable(df: DataFrame) = {
    // not tested
     df.write.format("org.apache.spark.sql.cassandra").options(Map("table" -> "airlinedata", "keyspace" -> "airlinepoc")).mode(SaveMode.Append).save()
  }

}
