package com.pms.easy_book.repo;

import com.pms.easy_book.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentOrder,Long> {


    PaymentOrder findByOrderId(String orderId);

    @Query("SELECT SUM(p.amount) FROM PaymentOrder p")
    Optional<Integer> getTotalPaymentAmount();
}
