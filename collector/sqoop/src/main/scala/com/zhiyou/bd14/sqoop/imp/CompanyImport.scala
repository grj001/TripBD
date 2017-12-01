package com.zhiyou.bd14.sqoop.imp

import java.util.Date

import com.zhiyou.bd14.common.DateUtil
import com.zhiyou.bd14.sqoop.SQClient
import org.apache.sqoop.client.SqoopClient
import org.apache.sqoop.model.{MFromConfig, MInputType, MToConfig}

object CompanyImport {
  val client = SQClient.client
  var yyyymmdd = DateUtil.convert2String(new Date(),"yyyyMMdd")

  //postgresql 的架包
  // 创建hdfslink
  // 创建postgresql link jdbc link

  //create job
  def createJob() = {

    val sql =
      """
        |select company_id
        |       , company_address
        |       , company_attr
        |       , company_boss
        |       , company_name
        |       , company_phone
        |from wsc.tb_company where ${CONDITIONS}
      """.stripMargin

    val job = client.createJob("btrip_pgbd", "btrip_hdfs")

    job.setName("btrip_company")

    val fromConfig = job.getFromJobConfig()
    val toConfig = job.getToJobConfig()

    showFromJobConfig(fromConfig)
    showToJobConfig(toConfig)


    //用sql则这个不用写
//    fromConfig.getStringInput("fromJobConfig.schemaName").setValue("wsc")
//    fromConfig.getStringInput("fromJobConfig.tableName").setValue("tb_company")
    fromConfig.getStringInput("fromJobConfig.sql").setValue(sql)
    //用sql这个需要设置
    fromConfig.getStringInput("fromJobConfig.partitionColumn").setValue("company_id")
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
      .setValue(s"/sqoop/btrip_pg/$yyyymmdd/btrip_company")
    //这否在原来的基础上添加文件
    toConfig.getBooleanInput("toJobConfig.appendMode")
      .setValue(true)




    val status = client.saveJob(job)
    if (status.canProceed()) {
      println("创建company_job成功")
    } else {
      println("创建company_job失败")
    }

  }


  def deleteJob(name:String) = {
    try{
      client.deleteJob(name)
    }catch{
      case e:Exception => e.printStackTrace()
    }
  }
  def startJob() = {
    client.startJob("btrip_company")
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


  def main(args: Array[String]): Unit = {

    yyyymmdd = args match {
      case Array(date) => date
      case  _ => yyyymmdd
    }

    deleteJob("btrip_company")
    createJob()
    startJob()
  }

}
