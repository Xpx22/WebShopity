package com.example.webshopity.dal.repositories;

import com.example.webshopity.dal.entities.AdminUser;
import com.example.webshopity.dal.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);
}
