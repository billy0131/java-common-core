package com.ecwalk.common.other.kafka.selfserial;

import java.nio.ByteBuffer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.ecwalk.common.other.kafka.DemoUser;

public class SelfSerializer implements Serializer<DemoUser>{

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] serialize(String topic, DemoUser data) {
		try{
			byte[] name;
			int nameSize;
			if(data==null){
				return null;
			}
			if(data.getName()!=null){
				name=data.getName().getBytes("UTF-8");
				nameSize=data.getName().length();
			}else{
				name=new byte[0];
				nameSize=0;
			}
			ByteBuffer buffer=ByteBuffer.allocate(4+4+nameSize);
			buffer.putInt(data.getId());
			buffer.putInt(nameSize);
			buffer.put(name);
			return buffer.array();
		}catch(Exception e){
			throw new SerializationException("Error serialize DemoUser:"+e);
		}

	}

}
