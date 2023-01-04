package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPerson(Person person);

    //    @Query(value = "select * from orders where ((lower(number) LIKE'%?1') and ", nativeQuery = true)
//    List<Order> findByNumber(String number);
    @Query(value = "SELECT * FROM orders WHERE orders.number LIKE '%?1'", nativeQuery = true)
    List<Order> findByLastNumberSymbols(String lastSymbols);


}
