package wang.mh;

import org.junit.Test;
import wang.mh.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CommonTest {

    @Test
    public void readByte() throws Exception {
        User user = new User("hello");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(user);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Object object;
        try (ObjectInputStream ois = new ObjectInputStream(bis)){
            object =  ois.readObject();
        }
    }
}

