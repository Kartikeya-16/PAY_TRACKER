package com.paytracker.ledger_service.Service;

import com.paytracker.ledger_service.Dto.*;
import com.paytracker.ledger_service.Entity.Transaction;
import com.paytracker.ledger_service.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final TransactionRepository transactionRepository;

    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .amount(request.getAmount())
                .category(request.getCategory())
                .paymentMethod(request.getPaymentMethod())
                .description(request.getDescription())
                .tags(request.getTags())
                .transactionDate(request.getTransactionDate())
                .build();

        return TransactionResponse.fromEntity(transactionRepository.save(transaction));
    }

    public TransactionResponse getTransactionById(Long id) {
        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
        return TransactionResponse.fromEntity(t);
    }

    public List<TransactionResponse> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId).stream()
                .map(TransactionResponse::fromEntity)
                .toList();
    }

    public List<TransactionResponse> getTransactionsByUserAndType(Long userId, Transaction.TransactionType type) {
        return transactionRepository.findByUserIdAndType(userId, type).stream()
                .map(TransactionResponse::fromEntity)
                .toList();
    }

    public List<TransactionResponse> getTransactionsByUserAndCategory(Long userId, String category) {
        return transactionRepository.findByUserIdAndCategory(userId, category).stream()
                .map(TransactionResponse::fromEntity)
                .toList();
    }

    public List<TransactionResponse> getTransactionsByDateRange(Long userId, LocalDate start, LocalDate end) {
        return transactionRepository.findByUserIdAndTransactionDateBetween(userId, start, end).stream()
                .map(TransactionResponse::fromEntity)
                .toList();
    }

    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        t.setType(request.getType());
        t.setAmount(request.getAmount());
        t.setCategory(request.getCategory());
        t.setPaymentMethod(request.getPaymentMethod());
        t.setDescription(request.getDescription());
        t.setTags(request.getTags());
        if (request.getTransactionDate() != null) t.setTransactionDate(request.getTransactionDate());

        return TransactionResponse.fromEntity(transactionRepository.save(t));
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        }
        transactionRepository.deleteById(id);
    }
}