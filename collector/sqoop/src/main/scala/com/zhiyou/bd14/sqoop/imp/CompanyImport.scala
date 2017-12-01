package com.zhiyou.bd14.sqoop.imp

import org.apache.sqoop.client.SqoopClient
import org.apache.sqoop.model.{MFromConfig, MInputType, MToConfig}

object CompanyImport {
  val url = "http://master:12000/sqoop/"
  val client = new SqoopClient(url)


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
        |from wsc.tb_company where 1=1 and ${CONDITIONS}
      """.stripMargin

    val job = client.createJob("btrip_pgbd", "btrip_hdfs")

    job.setName("btrip_company01")

    val fromConfig = job.getFromJobConfig()
    val toConfig = job.getToJobConfig()

    showFromJobConfig(fromConfig)
    showToJobConfig(toConfig)


//    fromConfig.getStringInput("fromJobConfig.schemaName").setValue("wsc")
//    fromConfig.getStringInput("fromJobConfig.tableName").setValue("tb_company")
    fromConfig.getStringInput("fromJobConfig.sql").setValue(sql)
    fromConfig.getStringInput("fromJobConfig.partitionColumn").setValue("company_id")


    toConfig.getEnumInput("toJobConfig.outputFormat").setValue("TEXT_FILE")
    toConfig.getEnumInput("toJobConfig.compression").setValue("NONE")
    toConfig.getStringInput("toJobConfig.outputDirectory").setValue("/sqoop/btrip_pg")
    toConfig.getBooleanInput("toJobConfig.appendMode").setValue(true)




    val status = client.saveJob(job)
    if (status.canProceed()) {
      println("创建company_job成功")
    } else {
      println("创建company_job失败")
    }

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
    createJob()
  }

}
