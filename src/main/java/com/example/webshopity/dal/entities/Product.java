package com.example.webshopity.dal.entities;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;

    @Column(name = "category")
    private String category;

    @Column(name = "manufacturer")
    private String manufacturer;
/*
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
            fetch = FetchType.LAZY)*/
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public long getId() { return id; }
    public void setId(long id) { this.id = id;  }

    public String getName() { return name;}
    public void setName(String name) { this.name = name; }

    public String getDescription() {return description;}
    public void setDescription(String desc) {this.description = desc;}

    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
