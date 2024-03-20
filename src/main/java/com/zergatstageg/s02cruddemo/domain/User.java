package com.zergatstageg.s02cruddemo.domain;

import lombok.Data;


@Data(staticConstructor = "createUser")
public class User {
    private int id;
    private String firstName;
    private String lastName;
}
