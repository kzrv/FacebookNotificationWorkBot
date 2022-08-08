package cz.kzrv.FacebookNotificationWorkBot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Name can't be empty")
    @Column(name="name",unique = true)
    private String name;
    @Column(name="facebook_id")
    private String facebookId;
    @NotNull
    @Column(name="activated")
    private Boolean activated;
    @NotNull
    @Column(name="activated_code")
    private String code;
}
