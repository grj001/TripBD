package com.zhiyou.bd14.sqoop.imp

import com.zhiyou.bd14.sqoop.SQClient
import org.apache.sqoop.client.SqoopClient

object JobStarter {

  val client = SQClient.client

  //company
  def companyJob():PgImport = {
    val tableName = "tb_company"
    val sql =
      s"""
         |select *
         |from ${"wsc."+tableName}
         |where ${"${CONDITIONS}"}
      """.stripMargin
    val primaryKey = tableName.replace("tb_","")+"_id"
    val yyyymmdd = "20171202"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }


  //hotel
  def hotelJob() = {
    val tableName = "tb_hotel"
    val sql =
      s"""
         |select *
         |from ${"wsc."+tableName}
         |where ${"${CONDITIONS}"}
      """.stripMargin
    val primaryKey = tableName.replace("tb_","")+"_id"
    val yyyymmdd = "20171202"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }


  //buildinginfo
  def buildingJob():PgImport = {
    val tableName = "tb_buildinginfo"
    val sql =
      s"""
         |select *
         |from ${"wsc."+tableName}
         |where ${"${CONDITIONS}"}
      """.stripMargin
    val primaryKey = tableName.replace("tb_","")+"_id"
    val yyyymmdd = "20171202"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }


  //layers
  def layersJob():PgImport ={
    val tableName = "tb_layers"
    val sql =
      s"""
         |select *
         |from ${"wsc."+tableName}
         |where ${"${CONDITIONS}"}
      """.stripMargin
    val primaryKey = tableName.replace("tb_","")+"_id"
    val yyyymmdd = "20171202"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }


  //roominfoJob
  def roominfoJob():PgImport = {
    val tableName = "tb_roominfo"
    val sql =
      s"""
         |select *
         |from ${"wsc."+tableName}
         |where ${"${CONDITIONS}"}
      """.stripMargin
    val primaryKey = "roominfo_id"
    val yyyymmdd = "20171202"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }


  //roomtype
  def roomTypeJob() : PgImport = {
    val tableName = "tb_roomtype"
    val sql =
      s"""
         |select *
         |from wsc.tb_roomtype
         |where ${"${CONDITIONS}"}
      """.stripMargin
    val primaryKey = "room_type_id"
    val yyyymmdd = "20171202"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }

  def tbCustomerJob() = {
    val sql = "select * from wsc.tb_customer where ${CONDITIONS}"
    val yyyymmdd = "20171202"
    val tableName ="tb_customer"
    val primaryKey = "customer_id"
    new PgImport(SQClient.client,yyyymmdd,sql,tableName,primaryKey)
  }

  //tb_cust_source
  def tbCustSourceJob() = {
    val tableName = "tb_cust_source"
    val sql =
      s"""
         |select *
         |from ${"wsc."+tableName}
         |where ${"${CONDITIONS}"}
      """.stripMargin
    val primaryKey = "cust_source_id"
    val yyyymmdd = "20171202"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }


  def tbBookingJob() = {
    val yyyymmdd = "20171202"
    val sql =
      s"""
         |select
         |booking_id,
         |customer_id,
         |staff_id,
         |paytype,
         |paymoney,
         |valid_flag,
         |memo,
         |disable_reason,
         |to_char(operate_time,'YYYY-MM-DD HH24:MI:SS') operate_time,
         |disable_staff_id,
         |hotel_id,
         |sourcetype,
         |acct_id,
         |full_balance,
         |book_balance,
         |checkin_balance,
         |sum_fee,
         |derate_fee,
         |final_charge,
         |price_class,
         |price_type
         |from wsc.tb_booking
         |where to_char(operate_time,'YYYYMMDD')='20161202' and ${"$"}{CONDITIONS}
       """.stripMargin
    val tableName = "tb_booking"
    val primaryKey = "booking_id"
    new PgImport(client, yyyymmdd, sql, tableName, primaryKey)
  }



  def tbBookedroomJob() = {
    val yyyymmdd = "20171202"
    val sql =
      s"""
         |select a.bookedroom_id
         |  ,a.livetype
         |  ,a.room_money
         |  ,to_char(a.booktime, 'YYYYMMDD') booktime
         |  ,a.bookvalid_flag
         |  ,a.paymoney
         |  ,a.disable_reason
         |  ,a.booking_id
         |  ,a.roominfo_id
         |  ,a.disable_staff_id
         |  ,to_char(a.disable_time,'YYYY-MM-DD HH24:MI:SS') disable_time
         |  ,a.checkin_room_id
         |from wsc.tb_bookedroom a
         |inner join wsc.tb_booking b
         |on a.booking_id = b.booking_id
         |where to_char(b.operate_time, 'YYYYMMDD')='20161202'
         |and ${"$"}{CONDITIONS}
       """.stripMargin
    val tableName ="tb_bookedroom"
    val pcName = "bookedroom_id"
    new PgImport(SQClient.client,yyyymmdd,sql,tableName,pcName)
  }



  def doJob(importor:PgImport) = {
    importor.deleteJob()
    importor.createJob()
    importor.startJob()
  }





  def main(args: Array[String]): Unit = {

    doJob(tbBookedroomJob())
  }
}


