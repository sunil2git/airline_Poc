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
  "com.datastax.spark" %% "spark-cassandra-connector" % "2.3.0"
)

// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.0.1" % Test)

// https://mvnrepository.com/artifact/com.datastax.spark/spark-cassandra-connector-embedded
//libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector-embedded" % "2.3.0" % Test
// https://mvnrepository.com/artifact/com.datastax.spark/spark-cassandra-connector
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.3.0"

