package cz.kzrv.FacebookNotificationWorkBot.models;

import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="work_tomorrow")
@Getter
@Setter
@NoArgsConstructor
public class TomorrowShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="name_id",referencedColumnName = "name")
    private Person owner;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "shift")
    private TimeTable timeTable;
}
