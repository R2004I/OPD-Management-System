package com.pms.easy_book.repo;

import com.pms.easy_book.Enum.PaymentStatus;
import com.pms.easy_book.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentOrder,Long> {


    PaymentOrder findByOrderId(String orderId);

    @Query("SELECT SUM(p.amount) FROM PaymentOrder p")
    Optional<Integer> getTotalPaymentAmount();

    @Query("""
           SELECT COALESCE(SUM(p.amount), 0)
           FROM PaymentOrder p
           WHERE p.status = :status
           """)
    BigDecimal getTotalPaymentAmountByStatus(@Param("status") PaymentStatus status);
}
