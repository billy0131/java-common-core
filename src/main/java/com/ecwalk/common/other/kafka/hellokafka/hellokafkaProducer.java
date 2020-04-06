package com.ecwalk.common.other.kafka.hellokafka;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class hellokafkaProducer {
	
	public static void main(String[] args) {
		Properties properties=new Properties();
		properties.put("bootstrap.servers", "127.0.0.1:9092");//建议2个
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		
		KafkaProducer<String, String> producer=new KafkaProducer<>(properties);
		try {
			ProducerRecord<String, String> record;
			record=new ProducerRecord<String, String>("HELLO_TOPIC","teacher02","billy");
			//1
			Future<RecordMetadata> future=producer.send(record);
			RecordMetadata recordMetadata=future.get();
			if(null!=recordMetadata){
				System.out.println("success");
			}
			//2
			producer.send(record,new Callback() {
				
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			producer.close();
		}
		
		
	}

}
