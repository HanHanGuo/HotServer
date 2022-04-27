package com.xianguo.hotserver.socket.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.nio.ByteBuffer;

public class HotResponseOutputStream extends ServletOutputStream {

    ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);

    Integer length = 0;

    @Override
    public void write(int b) throws IOException {
        byteBuffer.put((byte) b);
        synchronized (length){
            length++;
        }
    }

    public byte[] read(){
        byte[] temp = new byte[length];
        for (Integer i = 0; i < length; i++) {
            temp[i] = byteBuffer.get(i);
        }
        return temp;
    }


    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
