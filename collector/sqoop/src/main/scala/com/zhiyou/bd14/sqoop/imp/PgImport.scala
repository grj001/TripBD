package com.zhiyou.bd14.sqoop.imp

import java.util.Date

import com.zhiyou.bd14.common.DateUtil
import com.zhiyou.bd14.sqoop.SQClient
import org.apache.sqoop.client.SqoopClient
import org.apache.sqoop.model.{MFromConfig, MToConfig}


class PgImport(val client:SqoopClient
               , val yyyymmdd:String
               , val sql:String
              , val tableName:String
              , val primaryKey:String) {
//  var yyyymmdd = DateUtil.convert2String(new Date(), "yyyyMMdd")
  var jobName = s"btrip_${tableName}_${yyyymmdd}"



  //postgresql 的架包
  // 创建hdfslink
  // 创建postgresql link jdbc link

  //create job
  def createJob() = {


    val job = client.createJob("btrip_pgbd", "btrip_hdfs")


    job.setName(jobName)

    val fromConfig = job.getFromJobConfig()
    val toConfig = job.getToJobConfig()

    showFromJobConfig(fromConfig)
    showToJobConfig(toConfig)


    //用sql则这个不用写
    //    fromConfig.getStringInput("fromJobConfig.schemaName").setValue("wsc")
    //    fromConfig.getStringInput("fromJobConfig.tableName").setValue(tableName)
    fromConfig.getStringInput("fromJobConfig.sql").setValue(sql)
    //用sql这个需要设置
    fromConfig.getStringInput("fromJobConfig.partitionColumn").setValue(primaryKey)
    //    fromConfig.getStringInput("fromJobConfig.boundaryQuery").setValue("false")


    //Enum(枚举)的直接填字符串
    toConfig.getEnumInput("toJobConfig.outputFormat")
      .setValue("PARQUET_FILE")
    toConfig.getEnumInput("toJobConfig.compression")
      .setValue("NONE")
    //sqoop导入数据到hdfs上目录配置
    //根路径 /sqoop/btrip_pg
    // yyyymmdd/tableName
    toConfig.getStringInput("toJobConfig.outputDirectory")
      .setValue(s"/sqoop/btrip_pg/$yyyymmdd/$tableName")
    //这否在原来的基础上添加文件
    toConfig.getBooleanInput("toJobConfig.appendMode")
      .setValue(true)


    val status = client.saveJob(job)
    if (status.canProceed()) {
      println(s"创建${jobName}成功")
    } else {
      println(s"创建${jobName}失败")
    }

  }


  def deleteJob() = {
    try {
      client.deleteJob(jobName)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  def startJob() = {
    client.startJob(jobName)
    client.getDriver()
  }


  def showFromJobConfig(configs: MFromConfig) = {
    val configList = configs.getConfigs()
    for (i <- 0 until configList.size()) {
      val config = configList.get(i)
      val inputs = config.getInputs()
      for (j <- 0 until inputs.size()) {
        println(inputs.get(j))
      }
    }
  }

  def showToJobConfig(configs: MToConfig) = {
    val configList = configs.getConfigs()
    for (i <- 0 until configList.size()) {
      val config = configList.get(i)
      val inputs = config.getInputs()
      for (j <- 0 until inputs.size()) {
        println(inputs.get(j))
      }
    }
  }




}