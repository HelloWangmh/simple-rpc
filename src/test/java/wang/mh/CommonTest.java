package wang.mh;

import org.junit.Test;
import wang.mh.model.User;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class CommonTest {

    @Test
    public void readByte() throws Exception {

        User user = new User("1");
        Method method = user.getClass().getMethod("setName", String.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getType().getSimpleName());
        }

    }
}

