package com.gamestore.gamestore.entities;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
