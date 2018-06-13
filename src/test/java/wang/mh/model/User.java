package wang.mh.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
public class User implements Serializable {

    private String name;

    public User(String name) {
        this.name = name;
    }

}
