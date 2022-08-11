package cz.kzrv.FacebookNotificationWorkBot.models;

import cz.kzrv.FacebookNotificationWorkBot.util.TimeTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "work_today")
public class TodayShift{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="name_id",referencedColumnName = "name")
    private Person owner;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "shift")
    private TimeTable timeTable;
    @Column(name="sent")
    private Boolean sent;
}
