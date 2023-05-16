package com.nhom10.touringweb.model.user;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_tour")
    private Long idTour;

    @Column(name = "star")
    private int star;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "likes")
    private int likes;

    @Column(name = "id_reply")
    private Long idReply;

    public Comment() {
    }

    public Comment(int idUser, Long idTour, int star, String content, String createAt, int likes, Long idReply) {
        this.idUser = idUser;
        this.idTour = idTour;
        this.star = star;
        this.content = content;
        this.createAt = createAt;
        this.likes = likes;
        this.idReply = idReply;
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

    public Long getIdTour() {
        return idTour;
    }

    public void setIdTour(Long idTour) {
        this.idTour = idTour;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Long getIdReply() {
        return idReply;
    }

    public void setIdReply(Long idReply) {
        this.idReply = idReply;
    }
}
