package com.example.myStore.Domain;

import javax.persistence.*;

import javax.persistence.Entity;
import java.util.*;

@Entity
@Table(name = "ord")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private int cost;

    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ElementCollection
    private Map<Long, Long> orderMap = new HashMap<>();

    boolean active = true;

    public Order() {
    }

    public Order(Cart cart) {
        this.cart = cart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Map<Long, Long> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<Long, Long> orderMap) {
        this.orderMap = orderMap;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}