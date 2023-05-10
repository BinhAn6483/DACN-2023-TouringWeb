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

    @Column(name = "title")
    private String tile;

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

    @Column(name = "price_sale")
    private double priceSale;

    @Column(name = "location")
    private String location;

    public Tour(String name, String startingPoint, int category, String time, int sale, double price, String imgMain, String location) {
        this.name = name;
        this.startingPoint = startingPoint;
        this.category = category;
        this.time = time;
        this.sale = sale;
        this.price = price;
        this.imgMain = imgMain;
        this.location = location;
    }
    public Tour(String name,String title, String startingPoint, int category, String time, int sale, double price, String imgMain, String location) {
        this.name = name;
        this.tile = title;
        this.startingPoint = startingPoint;
        this.category = category;
        this.time = time;
        this.sale = sale;
        this.price = price;
        this.imgMain = imgMain;
        this.location = location;
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

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public void setPriceSale(double priceSale) {
        this.priceSale = priceSale;
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
        if (sale == 0) {
            return price;
        } else {
            return Math.round((price - ((sale * price) / 100)) / 10000.0) * 10000;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startingPoint='" + startingPoint + '\'' +
                ", category=" + category +
                ", time='" + time + '\'' +
                ", sale=" + sale +
                ", price=" + price +
                ", imgMain='" + imgMain + '\'' +
                ", viewCount=" + viewCount +
                ", priceSale=" + priceSale +
                ", location='" + location + '\'' +
                '}';
    }
}
