package com.nhom10.touringweb.model.user;

import javax.persistence.*;

@Entity
@Table(name = "link_img")
public class LinkImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tour")
    private Long idTour;

    @Column(name = "name_img")
    private String nameImg;

    @Column(name = "level")
    private int level;

    public LinkImg(Long idTour, String nameImg, int level) {
        this.idTour = idTour;
        this.nameImg = nameImg;
        this.level = level;
    }

    public LinkImg() {
    }

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

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
