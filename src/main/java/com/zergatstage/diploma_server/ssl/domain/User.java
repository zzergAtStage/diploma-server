package com.zergatstage.diploma_server.ssl.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data(staticConstructor = "createUser")
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String password;
    private String userRole;
    private String userAppLicense;
}
