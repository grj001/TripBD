package com.zhiyou.bd14.sqoop.link

import org.apache.sqoop.client.SqoopClient
import org.apache.sqoop.model.{MConfig, MInput, MLink}

object LinkCreator {
  val url = "http://master:12000/sqoop/"
  val client = new SqoopClient(url)

  // 创建job , job中把Company的数据导入hdfs上
  def createHdfsLink() = {
    val hdfsLink = client
      .createLink("hdfs-connector")


    val linkConfig = hdfsLink
      .getConnectorLinkConfig()

    val configs:java.util.List[MConfig] =
      linkConfig.getConfigs()

    printLinkConfiguration(configs)


    linkConfig.getStringInput("linkConfig.uri")
      .setValue("hdfs://master:9000")

    //hadoop的配置文件路径
    linkConfig.getStringInput("linkConfig.confDir")
      .setValue("/opt/Software/Hadoop/hadoop-2.7.3/etc/hadoop")
//    linkConfig.getMapInput("linkConfig.configOverrides").setValue()

    hdfsLink.setName("btrip_hdfs")
    val status = client.saveLink(hdfsLink)


    if(status.canProceed){
      println("hdfs-link创建成功")
    }else{
      println("hdfs-link创建失败")
    }


  }

  def createPostgresqlLink() = {
    val pglink = client.createLink("generic-jdbc-connector")
    val linkConfig = pglink.getConnectorLinkConfig()

    val configs:java.util.List[MConfig] =
      linkConfig.getConfigs()

    printLinkConfiguration(configs)

    linkConfig.getStringInput("linkConfig.jdbcDriver")
      .setValue("org.postgresql.Driver")

    linkConfig.getStringInput("linkConfig.connectionString")
      .setValue("jdbc:postgresql://192.168.58.1:5432/WscHMS")

    linkConfig.getStringInput("linkConfig.username")
      .setValue("postgres")
    linkConfig.getStringInput("linkConfig.password")
      .setValue("root")
    linkConfig.getStringInput("dialect.identifierEnclose")
      .setValue("")


    pglink.setName("btrip_pgbd")
    val status = client.saveLink(pglink)

    if(status.canProceed){
      println("hdfs-link创建成功")
    }else{
      println("hdfs-link创建失败")
    }

  }

  //打印link配置项信息
  def printLinkConfiguration(configs:java.util.List[MConfig]) = {
    for(i <- 0 until configs.size()){

      val inputs:java.util.List[MInput[_]] =
        configs.get(i).getInputs()

      for(j <- 0 until inputs.size()){
        val input = inputs.get(j)
        println(input)
      }
    }
  }


  def main(args: Array[String]): Unit = {
    createHdfsLink()
//    createPostgresqlLink()
  }

}
