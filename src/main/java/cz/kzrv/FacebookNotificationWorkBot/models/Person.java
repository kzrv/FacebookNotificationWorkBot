package cz.kzrv.FacebookNotificationWorkBot.models;

import cz.kzrv.FacebookNotificationWorkBot.util.StatesOfBot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "person")
public class Person implements Serializable {
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
    @Column(name="admin")
    private Boolean admin;
    @Column(name="state")
    @Enumerated(value = EnumType.STRING)
    private StatesOfBot statesOfBot;
    @Column(name = "notification_token")
    private String token;
    @Column(name = "available_notifications")
    private Boolean availNotif;
    @OneToMany(mappedBy = "owner")
    private List<TomorrowShift> tomorrow;

    @Override
    public String toString() {
        return "\n"+name+" : Facebook ID: " + facebookId + "\nActivated: "+activated + "\nActivated code: "+ code+"\n";
    }
}
