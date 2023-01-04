package com.example.springsecurityapplication.models;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileName;
    // устанавливаем связь с таблицей товаров
    //fetch = FetchType.EAGER позволит погрузить товар при работе с многими фотографиями
    //optional = false - данная таблица не будет отвечать за данную связь
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public Product product;

    public Image(int id, String fileName, Product product) {
        this.id = id;
        this.fileName = fileName;
        this.product = product;
    }
    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
