package com.nhom10.touringweb.model.user;

import javax.persistence.*;

@Entity
@Table(name = "wish_list")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tour")
    private Long idTour;

    @Column(name = "id_user")
    private int idUser;

    public WishList( Long idTour, int idUser) {
        this.idTour = idTour;
        this.idUser = idUser;
    }

    public WishList() {

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
