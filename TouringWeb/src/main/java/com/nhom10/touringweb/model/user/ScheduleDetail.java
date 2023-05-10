package com.nhom10.touringweb.model.user;

import javax.persistence.*;

@Entity
@Table(name = "schedule_detail")
public class ScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_schedule")
    private Long idSchedule;

    @Column(name = "time")
    private String time;

    @Column(name = "content")
    private String content;

    @Column(name = "level")
    private int level;

    public ScheduleDetail(Long idSchedule, String time, String content, int level) {
        this.idSchedule = idSchedule;
        this.time = time;
        this.content = content;
        this.level = level;
    }

    public ScheduleDetail(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(Long idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
