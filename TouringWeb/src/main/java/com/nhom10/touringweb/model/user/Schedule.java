package com.nhom10.touringweb.model.user;

import javax.persistence.*;

@Entity
@Table(name="schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tour")
    private Long idTour;

    @Column(name = "title")
    private String title;

    @Column(name = "level")
    private int level;

    public Schedule(Long idTour, String title, int level) {
        this.idTour = idTour;
        this.title = title;
        this.level = level;
    }

    public Schedule() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTour() {
        return idTour;
    }

    public void setIdTour(Long idTour) {
        this.idTour = idTour;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
