package wang.mh.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {

    private String name;

    public User(String name) {
        this.name = name;
    }

}
