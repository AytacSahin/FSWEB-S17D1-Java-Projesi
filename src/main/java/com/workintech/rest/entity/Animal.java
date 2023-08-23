package com.workintech.rest.entity;

import org.springframework.stereotype.Component;

public class Animal {
    private int id;
    private String name;

    // Boş constructor tanımlıyoruz. Component ilk oluşturulduğunda, burada değişkenlerden bağımsız bir nesne oluşabilmesi için.
    public Animal() {
    }

    public Animal(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
