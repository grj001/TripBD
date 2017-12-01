package com.zhiyou.bd14.sqoop

import org.apache.sqoop.client.SqoopClient

object SQClient {
  val url = "http://master:12000/sqoop/"
  val client = new SqoopClient(url)
}
