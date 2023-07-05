package com.guangyao.bookstore.domain.repository;

import com.guangyao.bookstore.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    Payment getByPayId(String payId);
}
