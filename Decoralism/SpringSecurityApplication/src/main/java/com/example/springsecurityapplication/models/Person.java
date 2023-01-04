package com.example.springsecurityapplication.models;

import jdk.jfr.Registered;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Логин не может быть пустым")
    @Size(min = 5, max = 100, message = "Логин должен быть от 5 до 100 символов")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Пароль не может быть пустым")
//Сняли из-за того что добавили валидацию с регулярным выражением    @Size(min = 5, max = 100, message = "Паро должен быть от 5 до 100 символов")
    @Column(name = "password")
//    @Max(value=100, message = "Пароль должен быть до 100 символов")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Необходимы строчные и прописные латинские буквы, цифры, спецсимволы. Минимум 8 символов в пароле")
    private String password;

    @Column(name = "role")
    private String role;

    //02-20-00 29/11 many to many реализуется через третью смежную вспомогательную таблицу,указываем что таблица будет называться product_cart и указываем какие поля будут в данной таблице, также мы должны указать эту связь в таблице продукт
    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> product;

    @OneToMany(mappedBy = "person")
    private List<Order> orderList;

    public Person() {


    }

    public Person(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
