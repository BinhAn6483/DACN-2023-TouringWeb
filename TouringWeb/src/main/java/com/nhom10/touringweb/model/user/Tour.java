package com.nhom10.touringweb.model.user;

import javax.persistence.*;

@Entity
@Table(name = "tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "starting_point")
    private String startingPoint;

    @Column(name = "category")
    private int category;

    @Column(name = "time")
    private String time;

    @Column(name = "sale")
    private int sale;

    @Column(name = "price")
    private double price;

    @Column(name = "img_main")
    private String imgMain;

    @Column(name = "view_count")
    private int viewCount;

    public Tour(String name, String startingPoint, int category, String time, int sale, double price, String imgMain) {
        this.name = name;
        this.startingPoint = startingPoint;
        this.category = category;
        this.time = time;
        this.sale = sale;
        this.price = price;
        this.imgMain = imgMain;
    }

    public Tour() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgMain() {
        return imgMain;
    }

    public void setImgMain(String imgMain) {
        this.imgMain = imgMain;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public double getPriceSale() {
        if(getSale() == 0) {
            return getPrice();
        }else {
            return getPrice() - ((getSale() * getPrice())/100);
        }
    }
}