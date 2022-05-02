package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.Order;
import com.example.webshopity.dal.entities.OrderItem;
import com.example.webshopity.dal.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    @Transactional
    void deleteByOrder(Order order);
    @Query("select oi from OrderItem oi where oi.order.id = :orderId")
    Optional<List<OrderItem>> findProductsByOrderId(@Param("orderId") long orderId);
}
