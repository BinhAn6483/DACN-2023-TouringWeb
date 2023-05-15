package com.nhom10.touringweb.model.user;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "departure_dates")
public class DepartureDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tour")
    private Long idTour;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "quantity")
    private int quantity;

    public DepartureDates() {
    }

    public DepartureDates(Long idTour, Date dateStart, Date dateEnd, int quantity) {
        this.idTour = idTour;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.quantity = quantity;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "DepartureDates{" +
                "id=" + id +
                ", idTour=" + idTour +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", quantity=" + quantity +
                '}';
    }
}
