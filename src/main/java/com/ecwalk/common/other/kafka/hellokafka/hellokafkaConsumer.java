package com.ecwalk.common.other.kafka.hellokafka;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class hellokafkaConsumer {

	public static void main(String[] args) {
		Properties properties=new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");//建议2个
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");

		KafkaConsumer<String, String> consumer=new KafkaConsumer<>(properties);
		try{
			consumer.subscribe(Collections.singletonList("HELLO_TOPIC"));//订阅
			while (true) {
				ConsumerRecords<String, String> records= consumer.poll(500);
				for(ConsumerRecord<String, String> record:records ){
					System.out.println(String.format("topic：%s,分区：%d,偏移量：%d,key:%s,value:%s",
							record.partition(),record.offset(),record.key(),record.value()));
				}
			}
		}finally{
			consumer.close();
		}
	}

}
