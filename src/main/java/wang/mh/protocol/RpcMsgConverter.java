package wang.mh.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

@Slf4j
public class RpcMsgConverter {

    private static final int BASIC_LENGTH = 4;  //length

    public static ByteBuf encode(Serializable message) throws IOException {
        byte[] msgBytes = obj2Byte(message);
        ByteBuf lengthBuf = Unpooled.buffer(4);
        lengthBuf.writeInt(msgBytes.length);
        return Unpooled.wrappedBuffer(lengthBuf.array(), msgBytes);
    }

    public static boolean decode(ByteBuf byteBuf, List<Object> out, boolean isRequest) throws IOException, ClassNotFoundException{
        if (byteBuf.readableBytes() < BASIC_LENGTH) {
            return false;
        }
        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        if (byteBuf.readableBytes() < (length)) {
            byteBuf.resetReaderIndex();//重置读索引
            return false;
        }
        byte[] msgBytes = new byte[length];
        byteBuf.readBytes(msgBytes, 0, length);


        if (isRequest) {
            RqMessage message = (RqMessage) byte2Object(msgBytes);
            out.add(message);
        } else {
            RsMessage message = (RsMessage) byte2Object(msgBytes);
            out.add(message);
        }
        return true;
    }

    private static byte[] obj2Byte(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }

    private static Object byte2Object(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(bis)){
            return  ois.readObject();
        }
    }


}
