package com.gamestore.gamestore.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private User buyer;
    private Set<Game> products;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @ManyToMany
    @JoinTable(name = "games_orders",
    joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    public Set<Game> getProducts() {
        return products;
    }

    public void setProducts(Set<Game> products) {
        this.products = products;
    }
}
