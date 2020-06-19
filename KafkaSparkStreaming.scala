package tech.logic.edu.spakr.steaming


import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.kafka010.ConsumerStrategies
import org.apache.spark.streaming.kafka010.LocationStrategies
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe




object KafkaSparkStreaming {
  def main(args: Array[String]): Unit={
    
    val conf = new SparkConf().setAppName("Kafka and Spark Streaming Integration").setMaster("local[*]")
    
    val ssc = new StreamingContext(conf, Seconds(20))
    
    
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "test_group_id",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

   val topics = Array("test")

   val preferredHosts = LocationStrategies.PreferConsistent

  
   val kafkaStream = KafkaUtils.createDirectStream[String, String](
        ssc,
        PreferConsistent,
        Subscribe[String, String](topics, kafkaParams)
      )
      
  
   val result = kafkaStream.map(record => (record.key, record.value))
  
   println("Job is running......."+result)
   
   result.foreachRDD(
        patient => {
          patient.collect().toBuffer.foreach(
            (x: (Any, String)) => {
              println(x)
  
            }
          )
        }
    
    )
  
   ssc.start()
   ssc.awaitTermination()
   
  }
  
}