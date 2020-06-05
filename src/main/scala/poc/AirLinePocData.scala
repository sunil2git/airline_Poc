package poc

import org.apache.log4j.{Level, Logger}
import poc.Analytics.projectAnalytics
import poc.utils.projectUtils


object airLinePocData {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = projectUtils.sparkSession()

    val airlineData = projectUtils.airlineData()

    val airportData = projectUtils.airportData()

    val routeData = projectUtils.routeData()

    val test = projectAnalytics.busyAirport2(routeData, airportData).limit(10)

     test.show()
    //projectUtils.csvWriter(test)
    println(Console.GREEN + " Data inserted ****")

    /** Read Data from Cassandra Tables */

    // Issue in loading data in airlineData table

    projectUtils.airlinDataCqlsh() //.show(numRows = 5)
    projectUtils.routeDataCqlsh() //.show(numRows = 5)
    projectUtils.airportDataCqlsh //.show(numRows = 5)


  }


}
