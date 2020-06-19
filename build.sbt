name := "airline_Poc"

version := "0.1"
scalaVersion := "2.11.8"

val sparkVersion = "2.3.0"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "mysql" % "mysql-connector-java" % "5.1.6",
  "com.datastax.spark" %% "spark-cassandra-connector-embedded" % "2.3.0",
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.3.0",
  "com.eed3si9n" % "sbt-assembly" % "0.13.6",
  "net.liftweb" %% "lift-json" % "3.4.1"
  //"com.eed3si9n" %% "sbt-assembly" % "sbt0.10.0_0.6"
//  "com.eed3si9n" % "sbt-assembly" % "0.13.0"
  //"com.lihaoyi" %% "ujson" % "0.6.5"
)


