package com.zhiyou.bd14.sttomysql

import java.util.Properties

import org.apache.spark.sql.SparkSession


object HiveToMysql {


  val spark = SparkSession.builder()
    .master("local[*]")
    .appName("hive to mysql")
    .enableHiveSupport()
    .getOrCreate()


  import spark.implicits._
  import spark.sql

  //jdbc
  def readFromHiveJdbc() = {
    val tableFromHive = sql(
      """select *
        |from
        |st_hotel_roomtype_rate_201712
      """.stripMargin
    )
    tableFromHive.show()
    tableFromHive
  }


  def writeToMysql() = {
    val url = "jdbc:mysql://localhost:3306/btrip"
    val properties = new Properties()
    properties.put("user", "root")
    properties.put("password", "root")
    readFromHiveJdbc().write.jdbc(url, "st_hotel_roomtype_rate_201712", properties)
  }

  def main(args: Array[String]): Unit = {
    writeToMysql()
    test01()
  }


  def test01() = {
    val list = List[String]("a", "b", "c")

    println(list.mkString(","))
  }


}
