package com.zhiyou.bd14.sqoop.imp

import org.apache.sqoop.client.SqoopClient

object CompanyImport {
  val url = "http://master:12000/sqoop/"
  val client = new SqoopClient(url)


  // 创建hdfslink
  // 创建postgresql link jdbc link
  // 创建job , job中把Company的数据导入hdfs上
  //
}
