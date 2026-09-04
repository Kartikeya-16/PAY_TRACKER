package com.paytracker.ledger_service.Repository;

import com.paytracker.ledger_service.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByUserIdAndType(Long userId, Transaction.TransactionType type);
    List<Transaction> findByUserIdAndCategory(Long userId, String category);
    List<Transaction> findByUserIdAndTransactionDateBetween(Long userId, LocalDate start, LocalDate end);
}