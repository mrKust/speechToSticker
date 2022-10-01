package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Client")
@Getter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String secondName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "password_log_in")
    private String password;

    @Column(name = "user_role")
    private String userRole;

    public Client(String firstName, String secondName, String emailAddress, String password, String userRole) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userRole = userRole;
    }
}
