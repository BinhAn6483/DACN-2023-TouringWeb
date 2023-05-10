package com.nhom10.touringweb.model.user;

import javax.persistence.*;

@Entity
@Table(name = "img_avatar")
public class ImgAvatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "img")
    private String img;

    public ImgAvatar(int idUser, String img) {
        this.idUser = idUser;
        this.img = img;
    }

    public ImgAvatar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
