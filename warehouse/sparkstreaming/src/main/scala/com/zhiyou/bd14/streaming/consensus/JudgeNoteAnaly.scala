package com.zhiyou.bd14.streaming.consensus

import com.zhiyou.bd14.streaming.StreamingUtil._

/**
  * Created by ThinkPad on 2017/12/5.
  * kafka中message的key和value都注意使用string类型
  */
object JudgeNoteAnaly {


  //从kafka获取judge_note中的数据
  def getJudgeNote() = {
    val topics = Array("judge_note")
    val judgeNote = getStreamingFromKafka(topics)
    judgeNote.map(x=>x.value()).print()
    judgeNote.map(x=>x.value())
  }







  def calcJudgeScore() = {
    val judgeNote = getJudgeNote()
    judgeNote.mapPartitions(x=>{
      val regex = "JudgeNote\\((.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*)\\)".r
      val date = "20171205"//dstream中获取的流数据中应该有每一条评论的时间，流数据中确实，需要采集数据的地方补上时间数据
      for(judge <- x) yield {
        judge match{
          case regex(hotelId,platform,userId,userName,userLevel,judgeTimes,coverCity,title,scoreSum,scoreDtl1,scoreDtl2,scoreDtl3,scoreDtl4
          ,liveDate,isPlateformOrdered,content) => ((date,hotelId,platform),(scoreSum,scoreDtl1,scoreDtl2,scoreDtl3,scoreDtl4))
        }
      }
    })
  }
  case class ScoreDtl(judgeNum:Int,goodNum:Int,mediaNum:Int,badNum:Int,sumScore:Double,safeScore:Double,nvScore:Double,cleanScore:Double,serviceScore:Double)




  def accumulateScore() = {
    val judgeScore = calcJudgeScore()
    //总评论数，好评评论数，中评评论数，差评评论数，综合总评分和，安全评分和，隔音评分和，卫生评分和，服务评分和
    //平均综合总评分，平均安全评分，平均隔音评分，平均卫生评分，平均服务评分
    val batMerge = judgeScore.mapValues(x=>{
      ScoreDtl(1,
        if(x._1.toDouble>3) 1 else 0 ,
        if(x._1.toDouble == 3) 1 else 0,
        if(x._1.toDouble <3) 1 else 0,
        x._1.toDouble,
        x._2.toDouble,
        x._3.toDouble,
        x._4.toDouble,
        x._5.toDouble
      )
    }).reduceByKey((d1,d2)=>{
      ScoreDtl(d1.judgeNum+d2.judgeNum,
        d1.goodNum+d2.goodNum,
        d1.mediaNum+d2.mediaNum,
        d1.badNum+d2.badNum,
        d1.sumScore+d2.sumScore,
        d1.safeScore+d2.safeScore,
        d1.nvScore+d2.nvScore,
        d1.cleanScore+d2.cleanScore,
        d1.serviceScore+d2.serviceScore
      )
    })
    batMerge.updateStateByKey((seq:Seq[ScoreDtl],os:Option[ScoreDtl])=>{
      os match{
        case Some(state) => if(seq.size>0){  //如果状态中已存在内容
          val scoreDtl = seq(0)
          Some(ScoreDtl(state.judgeNum+scoreDtl.judgeNum,state.goodNum+scoreDtl.goodNum,state.mediaNum+scoreDtl.mediaNum,state.badNum+scoreDtl.badNum,
            state.sumScore+scoreDtl.sumScore,state.safeScore+scoreDtl.safeScore,state.nvScore+scoreDtl.nvScore,state.cleanScore+scoreDtl.cleanScore
            ,state.serviceScore+scoreDtl.serviceScore ))
        }else{
          os
        }
        case _ => if(seq.size>0){           //如果状态中不存在内容
          Some(seq(0))
        }else{
          None
        }
      }
    }).mapValues(x=>{
      (x,x.sumScore*1.00/x.judgeNum,x.safeScore*1.00/x.judgeNum,x.nvScore*1.00/x.judgeNum
        ,x.cleanScore*1.00/x.judgeNum,x.serviceScore*1.00/x.judgeNum)
    }).print()
  }
  //把state里面的数据，实时更新到mysql或者hbase
  def persistState() = {
    val states = accumulateScore()
    //    states.foreachRDD()  保存到mysql或hbase
  }

  def main(args: Array[String]): Unit = {
    getJudgeNote()
//    accumulateScore()
    ssc.start()
    ssc.awaitTermination()
  }
}