package com.ecwalk.common.other.kafka.selfserial;

import java.nio.ByteBuffer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.ecwalk.common.other.kafka.DemoUser;

public class SelfDeserializer implements Deserializer<DemoUser>{

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public DemoUser deserialize(String topic, byte[] data) {
		try{
			if(data==null){
				return null;
			}
			if(data.length<8){
				throw new SerializationException("Error data size");
			}
			ByteBuffer buffer=ByteBuffer.wrap(data);
			int id;
			String name;
			int nameSize;
			id=buffer.getInt();
			nameSize=buffer.getInt();
			byte[] nameByte=new byte[nameSize];
			buffer.get(nameByte);
			name=new String(nameByte,"UTF-8");
			return new DemoUser(id,name);
		}catch(Exception e){
			throw new SerializationException("Error serialize DemoUser:"+e);
		}

	}

}
