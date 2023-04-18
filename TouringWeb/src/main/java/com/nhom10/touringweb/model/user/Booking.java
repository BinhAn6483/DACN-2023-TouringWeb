package com.nhom10.touringweb.model.user;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_tour")
    private Long idTour;

    @Column(name = "no_adults")
    private int noAdults;

    @Column(name = "no_children")
    private int noChildren;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "payment")
    private String payment;

    @Column(name = "status_payment")
    private String statusPayment;

    @Column(name = "status_tour")
    private String statusTour;


    @Column(name = "create_at")
    private String createAt;

    public Booking(int idUser, Long idTour, int noAdults, int noChildren,double totalPrice , Date dateStart,  String payment, String statusPayment, String statusTour ,String createAt) {
        this.idUser = idUser;
        this.idTour = idTour;
        this.noAdults = noAdults;
        this.noChildren = noChildren;
        this.totalPrice = totalPrice;
        this.dateStart = dateStart;
        this.payment = payment;
        this.statusPayment = statusPayment;
        this.statusTour = statusTour;
        this.createAt = createAt;
    }

    public Booking() {
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

    public int getNoAdults() {
        return noAdults;
    }

    public void setNoAdults(int noAdults) {
        this.noAdults = noAdults;
    }

    public int getNoChildren() {
        return noChildren;
    }

    public void setNoChildren(int noChildren) {
        this.noChildren = noChildren;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getStatusTour() {
        return statusTour;
    }

    public void setStatusTour(String statusTour) {
        this.statusTour = statusTour;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idTour=" + idTour +
                ", noAdults=" + noAdults +
                ", noChildren=" + noChildren +
                ", totalPrice=" + totalPrice +
                ", payment='" + payment + '\'' +
                ", statusPayment='" + statusPayment + '\'' +
                ", statusTour='" + statusTour + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}
