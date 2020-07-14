package poc.utils

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object ProjectUtils {

  /** Developer name : Sunil kumar  */


  /** Loading Data into respective dataFrames :   */
  val configData = sparkSession().read.option("multiline", true).json("/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/main/scala/poc/config.json")
  val cassandraConfig = configData.select("cassandraConfig").collect()(0).mkString("")

  def airlineData() = {
    val airlineDataPath = configData.select("airlineDataPath").collect()(0).mkString("")
    val airlineData = sparkSession().read.csv(airlineDataPath)
      .toDF(ColumnNames.AIRLINE_ID, ColumnNames.AIRLINE_NAME, ColumnNames.ALIAS, ColumnNames.IATA, ColumnNames.ICAO, ColumnNames.CALLSIGN, ColumnNames.COUNTRY, ColumnNames.ACTIVE).cache()
    airlineData
  }

  /*def routeData() = {
    val routeDataPath = configData.select("routeDataPath").collect()(0).mkString("")
    val columns = configData.select("routeColumns").collect()(0).mkString("")
    val routeData = sparkSession().read.csv(routeDataPath)
      .toDF(ColumnNames.AIRLINE_NAME, ColumnNames.AIRLINE_ID, ColumnNames.SOURCE_AIRPORT, ColumnNames.SOURCE_AIRPORT_ID, ColumnNames.DESTINATION_AIRPORT, ColumnNames.DESTINATION_AIRPORT_ID, ColumnNames.CODESHARE, ColumnNames.STOPS, ColumnNames.UNKNOWN)
    routeData
  }*/

  def routeData() = {
    val routeDataPath = configData.select("routeDataPath").collect()(0).mkString("")
    // val routeColumns = configData.select("routeColumns").collect()(0).mkString("")
    val routeData = sparkSession().read.csv(routeDataPath)
      .toDF("airlinename", "id", "source_airport", "source_airport_id", "destination_airport", "destination_airport_id", "CodeShare", "stops", "unknown")
    routeData
  }

  /* def routeData() = {
     val routeDataPath = configData.select("routeDataPath").collect()(0).mkString("")
     val routeColumns = configData.select("routeColumns").collect()(0).mkString("")
     val routeData = sparkSession().read.csv(routeDataPath)
       .toDF(routeColumns)
     routeData
   }*/

  /*  def airportData() = {
      val airportDataPath = configData.select("airportDataPath").collect()(0).mkString("")
      val airportData = sparkSession().read.csv(airportDataPath)
        .toDF("id", "airportName", "city", "country", "IATA/FAA", "ICAO", "latitude", "longitude", "Altitude", "Timezone", "DST", "place")
      airportData
    }*/

  def airportData() = {
    val airportDataPath = configData.select("airportDataPath").collect()(0).mkString("")
    val airportData = sparkSession().read.csv(airportDataPath)
      .toDF(ColumnNames.AIRLINE_ID, ColumnNames.AIRPORT_NAME, ColumnNames.CITY, ColumnNames.COUNTRY, ColumnNames.IATA_FAA, ColumnNames.ICAO,
        ColumnNames.LATITUDE, ColumnNames.LONGITUDE, ColumnNames.ALTITUDE, ColumnNames.TIMEZONE, ColumnNames.DST, ColumnNames.PLACE)
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

  /** spark session configuration, Entry point to spark :  */

  def sparkSession() = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("test sparkSession")
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .getOrCreate()
    spark
  }

  def airportDataCqlsh = {
    val airportDF = sparkSession().read
      .format("org.apache.spark.sql.cassandra")
      .options(Map("table" -> "airportdata", "keyspace" -> "airlinepoc")).load.cache()
    airportDF
  }


  def timeUtils() = {
    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
    var submittedDateConvert = new Date()
    val createdOn = dateFormatter.format(submittedDateConvert)
    createdOn
  }

  /** Writing data into output file :  */

  // airlineData.write.format("org.apache.spark.sql.cassandra").options(Map( "table" -> "airlinedata", "keyspace" -> "airlinepoc")).save()
  //  Below is tested for writing.
  //  airlineData.write.format("org.apache.spark.sql.cassandra").options(Map("table" -> "airlinedata", "keyspace" -> "airlinepoc")).mode(SaveMode.Append).save()

  def csvWriter(df: DataFrame) = {

    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
    var submittedDateConvert = new Date()
    val createdOn = dateFormatter.format(submittedDateConvert)
    df.write.format("csv").save(s"/Users/acs/Documents/sparkData/airlinePocRepo/airline_Poc/src/main/scala/poc/outputFiles/" + createdOn)
  }

  def saveInCassandraTable(df: DataFrame, table: String, keyspace: String) = {
    // not tested
    df.write.format("org.apache.spark.sql.cassandra").options(Map("table" -> table, "keyspace" -> keyspace)).mode(SaveMode.Append).save()
  }

}
