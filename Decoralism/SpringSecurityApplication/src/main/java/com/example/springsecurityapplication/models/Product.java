package com.example.springsecurityapplication.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Поле не может быть пустым")
    private String title;
    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Поле не может быть пустым")
    private String description;
    @Column(name = "price", nullable = false)
    @NotNull(message = "Поле не может быть пустым")
    @Min(value=1, message="Минимальная сумма не может быть меньше 1 рубля")
    private float price;
    @Column(name = "warehouse", nullable = false)
    @NotEmpty(message = "Поле не может быть пустым")
    private String warehouse;
    @Column(name = "seller", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Поле не может быть пустым")
    private String seller;
    //устанавливаем связь с моделью категорий
    @ManyToOne(optional = false)
    private Category category;

    //устанавливаем связь с классом изображений, устанавливаем свойство что при удалении объекта товара, будут удаляться связанные к нему фотографии, fetch = FetchType.LAZY - указываем что фото будет подгружаться когда они будут нужны,
    // mappedBy = "product" - указываем с каким полем мы хотим установить связь

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> imageList = new ArrayList<>();

    //02-25-00 29/11 many to many реализуется через третью смежную вспомогательную таблицу,указываем что таблица будет называться product_cart и указываем какие поля будут в данной таблице
    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> person;

    @OneToMany(mappedBy = "product")
    private List<Order> orderList;

    // Получаем объект фотографии, указываем через this что фотография будет привязана к текущему продукту и сохраняем в лист картинку, которую получили в данном методе.
    public void addImageToProduct(Image image){
        image.setProduct(this);
        imageList.add(image);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    private LocalDateTime dateTime;
    @PrePersist
    public void init(){
        dateTime=LocalDateTime.now();
    }

    public Product(int id, String title, String description, float price, String warehouse, String seller) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.warehouse = warehouse;
        this.seller = seller;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {

        return (price);

    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
